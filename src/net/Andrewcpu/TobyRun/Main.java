package net.Andrewcpu.TobyRun;

import com.mysql.jdbc.Buffer;
import net.Andrewcpu.TobyRun.game.field.Arena;
import net.Andrewcpu.TobyRun.utils.GameListener;
import net.Andrewcpu.TobyRun.utils.LocationManager;
import net.Andrewcpu.TobyRun.utils.npx.Interaction;
import net.Andrewcpu.TobyRun.utils.npx.NPC;
import net.Andrewcpu.TobyRun.utils.npx.NPCManager;
import net.Andrewcpu.TobyRun.utils.npx.NPCType;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

/**
 * Created by stein on 6/25/2016.
 */
public class Main extends JavaPlugin {
    public static String header = ChatColor.translateAlternateColorCodes('&',"&0&l>&4&l>&f&l> &e&l");
    private static Main instance = null;
    private GameListener gameListener;
    private Arena arena;

    public static Main getInstance(){
        return instance;
    }
    public void onEnable(){
        instance = this;
        arena = new Arena();
        gameListener = new GameListener();
        getServer().getPluginManager().registerEvents(gameListener,this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this,()->{
            getArena().tick();
        }, 20, 20);
        getArena().resetMap();
        setupNPCs();
        for(Player player : Bukkit.getOnlinePlayers()){
            arena.joinGame(player);
        }
    }

    public void onDisable(){
        NPCManager.cleanUp();
        gameListener.cleanUp();
        getArena().gameOver();
    }

    public void setupNPCs(){
        Vector directionMage=LocationManager.loadVector("MAGE");
        Location locationMage = LocationManager.loadLocation("MAGE");
        locationMage.setDirection(directionMage);
        
        Vector directionArcher=LocationManager.loadVector("ARCHER");
        Location locationArcher = LocationManager.loadLocation("ARCHER");
        locationArcher.setDirection(directionArcher);
        
        Vector directionSnowball=LocationManager.loadVector("SNOWBALL");
        Location locationSnowball = LocationManager.loadLocation("SNOWBALl");
        locationSnowball.setDirection(directionSnowball);
        
        Vector directionSword=LocationManager.loadVector("SWORD");
        Location locationSword = LocationManager.loadLocation("SWORD");
        locationSword.setDirection(directionSword);
        
        
        
        NPC darkLord = NPCManager.createNPC(NPCType.ZOMBIE, locationMage.add(0,1,0), new Interaction() {
            @Override
            public void onInteract(Player player) {
                getArena().selectKit(player,getArena().getMageKit());
            }
        }, ChatColor.GRAY + "" + ChatColor.BOLD + "Wizard of Darkness");
        NPC archer = NPCManager.createNPC(NPCType.ZOMBIE, locationArcher.add(0,1,0), new Interaction() {
            @Override
            public void onInteract(Player player) {
                getArena().selectKit(player,getArena().getArcherKit());
            }
        }, ChatColor.GOLD + "" + ChatColor.BOLD + "Mini Minotaur Hunter");
        NPC snowball = NPCManager.createNPC(NPCType.ZOMBIE, locationSnowball.add(0,1,0), new Interaction() {
            @Override
            public void onInteract(Player player) {
                getArena().selectKit(player,getArena().getSnowballKit());
            }
        }, ChatColor.WHITE + "" + ChatColor.BOLD + "Deadly Snowman");
        NPC swordsmen = NPCManager.createNPC(NPCType.ZOMBIE, locationSword.add(0,1,0), new Interaction() {
            @Override
            public void onInteract(Player player) {
                getArena().selectKit(player,getArena().getSwordKit());
            }
        }, ChatColor.AQUA + "" + ChatColor.BOLD + "Toby's Warrior");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("tr")){
            if(args[0].equalsIgnoreCase("test")){
                Zombie zombie = (Zombie)((Player)sender).getWorld().spawnEntity(((Player)sender).getLocation(), EntityType.ZOMBIE);
                zombie.teleport(((Player)sender));
                Vector v = zombie.getLocation().toVector().subtract(zombie.getWorld().getSpawnLocation().toVector()).multiply(-1);
                Location location = zombie.getLocation();
                location.setDirection(v);
//                zombie.setAI(false);
                zombie.teleport(location);
                Bukkit.getScheduler().scheduleSyncDelayedTask(this,()->zombie.setAI(false),20);
            }
            if(args[0].equalsIgnoreCase("end")){
                getArena().gameOver();
            }
            if(args[0].equalsIgnoreCase("start")){
                getArena().startGame();
            }
            if(args[0].equalsIgnoreCase("set")){
                LocationManager.saveLocation(args[1].toUpperCase(),((Player)sender).getLocation().getBlock().getLocation());
                LocationManager.saveVector(args[1].toUpperCase(),((Player)sender).getLocation().getDirection());
                sender.sendMessage(ChatColor.RED + "Set locations");
            }
        }
        if(command.getName().equalsIgnoreCase("join")){
            getArena().joinGame((Player) sender);
        }
        return true;
    }

    public GameListener getGameListener() {
        return gameListener;
    }

    public void setGameListener(GameListener gameListener) {
        this.gameListener = gameListener;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }
}
