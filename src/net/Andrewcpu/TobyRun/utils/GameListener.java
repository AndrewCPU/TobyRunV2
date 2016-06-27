package net.Andrewcpu.TobyRun.utils;

import net.Andrewcpu.TobyRun.*;
import net.Andrewcpu.TobyRun.game.field.types.GameState;
import net.Andrewcpu.TobyRun.game.field.Team;
import net.Andrewcpu.TobyRun.utils.npx.NPCManager;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_10_R1.BlockBeacon;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * Created by stein on 6/25/2016.
 */
public class GameListener implements Listener {
    private List<Player> leapCooldown =  new ArrayList<>();
    private List<Player> mageCooldown =  new ArrayList<>();
    private HashMap<Location,BlockState> blockStates = new HashMap<>();
    public void cleanUp(){
        for(BlockState blockState : blockStates.values()){
            blockState.update(true);
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Main main = Main.getInstance();
        if(main.getArena().getGameState()== GameState.IN_PROGRESS){
            Team team = main.getArena().getPlayerTeam(event.getPlayer());
            if(event.getTo().getBlock().getRelative(BlockFace.DOWN).getType()== Material.STAINED_GLASS){
                score(event.getTo(),team);
            }
            for(Team t : main.getArena().getTeams()){
                if(t==team)
                    continue;
                if(event.getTo().distance(t.getSpawnPoint())<=7){
                    Vector v = event.getTo().toVector().subtract(t.getSpawnPoint().toVector()).normalize();
                    event.getPlayer().setVelocity(v);
                }
            }
        }

        if(event.getTo().getBlockY()<=0){
//            main.getArena().respawnPlayer(event.getPlayer());
            event.getPlayer().damage(5);
            event.getPlayer().sendTitle(ChatColor.RED + "" + ChatColor.BOLD + "Exiting the playable area",ChatColor.DARK_RED + "" + ChatColor.BOLD + "Please return to the game");
            event.getPlayer().setVelocity(new Vector(0,0.5,0));
        }
        event.getPlayer().setFoodLevel(20);
    }
    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if(Main.getInstance().getArena().getGameState()==GameState.IN_PROGRESS)
            event.setCancelled(true);
        else{
            if(!event.getPlayer().isOp()){
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        event.setCancelled(event.getPlayer().getGameMode() != GameMode.CREATIVE ||Main.getInstance().getArena().getGameState()==GameState.IN_PROGRESS );
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getPlayer().getInventory().getItemInMainHand().getType()==Material.STONE_AXE && !leapCooldown.contains(event.getPlayer())){
            Vector v = event.getPlayer().getLocation().getDirection();
            v.setY(v.getY() + 1);
            event.getPlayer().setVelocity(v.add(event.getPlayer().getVelocity()));
            leapCooldown.add(event.getPlayer());
            event.getPlayer().sendMessage(Main.header + "You used leap!");
            Main.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(Main.getInstance(),()->{leapCooldown.remove(event.getPlayer());event.getPlayer().sendMessage(Main.header + "You can use leap again!");},100);
        }
        if(event.getPlayer().getInventory().getItemInMainHand().getType()==Material.BLAZE_ROD && !mageCooldown.contains(event.getPlayer())){
            Location loc = event.getPlayer().getEyeLocation();
            Location block = event.getPlayer().getTargetBlock(((Set<Material>)null),500).getLocation().add(0.5,0.5,0.5);
            Vector v = loc.toVector().subtract(block.toVector()).normalize().multiply(-1);
            Block tBlock = loc.getBlock();
            for(double i = 0; i<=500 && tBlock.getType()==Material.AIR; i+=0.5){
                loc = loc.add(v);
                tBlock = loc.getBlock();
                loc.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,loc,0,0,0,0.25);
                for(Entity entity : loc.getWorld().getEntities()){
                    if(entity == event.getPlayer())
                        continue;
                    if(entity.getLocation().distance(loc)<=1){
                        if(entity instanceof LivingEntity)
                            ((LivingEntity) entity).damage(((LivingEntity) entity).getMaxHealth() / 4);
                    }
                    if(entity instanceof LivingEntity){
                        if(((LivingEntity) entity).getEyeLocation().distance(loc)<=0.5)
                            ((LivingEntity) entity).damage(30);
                    }
                }
            }
            if(tBlock.getType()!=Material.AIR){
                TNTPrimed tntPrimed = (TNTPrimed)tBlock.getWorld().spawnEntity(tBlock.getLocation(),EntityType.PRIMED_TNT);
                tntPrimed.setFuseTicks(0);
            }
            mageCooldown.add(event.getPlayer());
            event.getPlayer().sendMessage(Main.header + "You shot a fire ball!");
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),()->{
                mageCooldown.remove(event.getPlayer());
                event.getPlayer().sendMessage(Main.header + "You can shoot a fire ball again!");
            }, 5 * 20);
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event){

        for(Team t : Main.getInstance().getArena().getTeams()){
            if(event.getLocation().distance(t.getSpawnPoint())<=7){
                if(event.getEntity() instanceof Projectile){
                    Projectile projectile = (Projectile)event.getEntity();
                    if(projectile.getShooter() instanceof Player)
                        ((Player)(projectile.getShooter())).setVelocity(new Vector(0,1,0));
                }
                event.setCancelled(true);
                return;
            }
        }

        for(Entity e : event.getEntity().getNearbyEntities(5,5,5)){
            Location location = e.getLocation();
            int force = (7 - (int)location.distance(event.getLocation()) + 1) / 2;
            Vector v = location.toVector().subtract(event.getLocation().toVector()).multiply(force).normalize();
            v.setY(force / 2);
            e.setVelocity(v);
        }


        Random r = new Random();
        for(Block b : event.blockList()){
            blockStates.put(b.getLocation(),b.getState());
            b.setType(Material.AIR);
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),()->{blockStates.get(b.getLocation()).update(true);blockStates.remove(b.getLocation());},r.nextInt(30 * 20));
        }
        event.blockList().clear();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        event.setKeepInventory(true);
        event.setDeathMessage(null);
        Main.getInstance().getArena().respawnPlayer(event.getEntity());
    }
    public void score(Location location,Team team){
        Main main = Main.getInstance();
        int points = 0;
        List<Block> blocks = new ArrayList<>();
        blocks.add(location.getBlock().getRelative(BlockFace.DOWN));
        blocks.add(location.getBlock().getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN));
        blocks.add(location.getBlock().getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN));
        blocks.add(location.getBlock().getRelative(BlockFace.NORTH).getRelative(BlockFace.DOWN));
        blocks.add(location.getBlock().getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN));
        blocks.add(location.getBlock().getRelative(BlockFace.NORTH_EAST).getRelative(BlockFace.DOWN));
        blocks.add(location.getBlock().getRelative(BlockFace.NORTH_WEST).getRelative(BlockFace.DOWN));
        blocks.add(location.getBlock().getRelative(BlockFace.SOUTH_EAST).getRelative(BlockFace.DOWN));
        blocks.add(location.getBlock().getRelative(BlockFace.SOUTH_WEST).getRelative(BlockFace.DOWN));
        for(Block block : blocks){
            if(team==null)
                break;
            if(block.getData()!=team.getColorData() && block.getType()==Material.STAINED_GLASS){
                points++;
                Team blockOwner = main.getArena().getTeamByColor(block.getData());
                block.setData((byte)team.getColorData());
                if(blockOwner==null || blockOwner == team) {
                    continue;
                }
                blockOwner.setScore(blockOwner.getScore()-1);
            }
        }
        blocks.clear();
        team.setScore(team.getScore() + points);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        event.setCancelled(NPCManager.isInteractable(event.getEntity()) || event.getCause()== EntityDamageEvent.DamageCause.FALL || Main.getInstance().getArena().getGameState()==GameState.JOINABLE || Main.getInstance().getArena().getGameState()==GameState.STARTING);
        if(event.getEntity() instanceof Player){
            if(((Player) event.getEntity()).getHealth() - event.getDamage() <= 0){
//                Main.getInstance().getArena().respawnPlayer((Player)event.getEntity());
//                event.setDamage(0);
            }
        }
    }
    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event){
        if(NPCManager.isInteractable(event.getRightClicked()) && event.getHand()== EquipmentSlot.HAND){
            NPCManager.getInteractableEntity(event.getRightClicked()).getInteraction().onInteract(event.getPlayer());
        }
    }
    @EventHandler
    public void onTarget(EntityTargetEvent event){
        event.setTarget(null);
        event.setCancelled(true);
    }
    @EventHandler
    public void onIgnite(EntityCombustEvent event){
        event.setCancelled(true);
    }
    @EventHandler
    public void onLand(ProjectileHitEvent event){
        if(event.getEntity().getShooter() instanceof Player) {
            Player player = (Player) event.getEntity().getShooter();
            Team team = Main.getInstance().getArena().getPlayerTeam(player);
            score(event.getEntity().getLocation(),team);
            if(event.getEntity() instanceof Arrow)
                event.getEntity().remove();
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        event.setJoinMessage("");
        Main.getInstance().getArena().joinGame(event.getPlayer());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        ChatColor color = ChatColor.YELLOW;
        if(Main.getInstance().getArena().getPlayerTeam(event.getPlayer())!=null)
            color = Main.getInstance().getArena().getPlayerTeam(event.getPlayer()).getChatColor();
        event.setFormat(color + event.getPlayer().getDisplayName() + " " + ChatColor.RESET + event.getMessage());
    }

}
