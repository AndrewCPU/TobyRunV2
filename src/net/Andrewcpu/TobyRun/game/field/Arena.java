package net.Andrewcpu.TobyRun.game.field;

import net.Andrewcpu.MinigameAPI.ActionBar;
import net.Andrewcpu.MinigameAPI.scoreboard.GameBoard;
import net.Andrewcpu.TobyRun.Main;
import net.Andrewcpu.TobyRun.game.field.types.GameState;
import net.Andrewcpu.TobyRun.game.field.types.TeamColor;
import net.Andrewcpu.TobyRun.game.kits.Kit;
import net.Andrewcpu.TobyRun.game.kits.KitContents;
import net.Andrewcpu.TobyRun.game.kits.KitType;
import net.Andrewcpu.TobyRun.game.kits.TickKit;
import net.Andrewcpu.TobyRun.utils.LocationManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by stein on 6/25/2016.
 */
public class Arena {
    private List<Team> teams = new ArrayList<>();
    private GameState gameState = GameState.JOINABLE;
    private int maxTime = 500;
    private int time = 0;
    private int countdown = 10;
    private GameBoard gameBoard = new GameBoard(ChatColor.RED  + "" + ChatColor.BOLD + "Toby" + ChatColor.WHITE + ChatColor.BOLD + "Run", true);
    private Location lobby = LocationManager.loadLocation("Lobby"), corner1 = LocationManager.loadLocation("CORNER1"), corner2 = LocationManager.loadLocation("CORNER2");
    private HashMap<Player,Kit> kits = new HashMap<>();
    private Kit archerKit;
    private Kit mageKit;
    private Kit snowballKit;
    private Kit swordKit;
    public Arena(){
        teams.add(createTeam(TeamColor.BLACK, ChatColor.DARK_GRAY));
        teams.add(createTeam(TeamColor.GRAY,ChatColor.GRAY));
        teams.add(createTeam(TeamColor.GREEN,ChatColor.GREEN));
        teams.add(createTeam(TeamColor.RED,ChatColor.RED));
        setupKits();
    }
    public void setupKits(){
        List<ItemStack> archerContents = new ArrayList<>();
        List<ItemStack> mageContents = new ArrayList<>();
        List<ItemStack> snowballContents= new ArrayList<>();
        List<ItemStack> swordContents= new ArrayList<>();
        ItemStack leap = new ItemStack(Material.STONE_AXE,1);
        ItemMeta leapM = leap.getItemMeta();
        leapM.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Leap");
        leap.setItemMeta(leapM);

        ItemStack wand = new ItemStack(Material.BLAZE_ROD, 1);
        ItemMeta wandM = wand.getItemMeta();
        wandM.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Magic Staff");
        wand.setItemMeta(wandM);


        archerContents.add(new ItemStack(Material.BOW, 1));
        archerContents.add(leap);

        mageContents.add(wand);
        mageContents.add(new ItemStack(Material.GOLD_SWORD,1));


        snowballContents.add(new ItemStack(Material.SNOW_BALL,16));
        snowballContents.add(new ItemStack(Material.IRON_SWORD));


        swordContents.add(new ItemStack(Material.DIAMOND_SWORD));
        swordContents.add(leap);


        archerKit = new Kit(KitType.ARCHER, new KitContents(archerContents), new TickKit() {
            @Override
            public void onTick(Player player) {
                player.getInventory().addItem(new ItemStack(Material.ARROW,1));
            }
        });
        mageKit = new Kit(KitType.MAGE, new KitContents(mageContents), new TickKit() {
            @Override
            public void onTick(Player player) {

            }
        });
        snowballKit = new Kit(KitType.SNOWBALL, new KitContents(snowballContents), new TickKit() {
            @Override
            public void onTick(Player player) {
                player.getInventory().addItem(new ItemStack(Material.SNOW_BALL, 1));
            }
        });
        swordKit = new Kit(KitType.SWORD, new KitContents(swordContents), new TickKit() {
            @Override
            public void onTick(Player player) {

            }
        });

        archerKit.addLine("+1 Bow");
        archerKit.addLine("+1 Arrow/Second");
        archerKit.addLine("+Ability Leap");

        mageKit.addLine("+1 Blaze Rod");
        mageKit.addLine("+1 Golden Sword");

        snowballKit.addLine("+1 Iron Sword");
        snowballKit.addLine("+1 Snowball/Second");

        swordKit.addLine("+1 Diamond Sword");
        swordKit.addLine("+Ability Leap");
    }
    public Team createTeam(TeamColor teamColor, ChatColor chatColor){
        Team team = new Team(LocationManager.loadLocation(teamColor.toString().toUpperCase()),teamColor, chatColor);
        return team;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public HashMap<Player, Kit> getKits() {
        return kits;
    }

    public void setKits(HashMap<Player, Kit> kits) {
        this.kits = kits;
    }

    public Kit getArcherKit() {
        return archerKit;
    }

    public void setArcherKit(Kit archerKit) {
        this.archerKit = archerKit;
    }

    public Kit getMageKit() {
        return mageKit;
    }

    public void setMageKit(Kit mageKit) {
        this.mageKit = mageKit;
    }

    public Kit getSnowballKit() {
        return snowballKit;
    }

    public void setSnowballKit(Kit snowballKit) {
        this.snowballKit = snowballKit;
    }

    public Kit getSwordKit() {
        return swordKit;
    }

    public void setSwordKit(Kit swordKit) {
        this.swordKit = swordKit;
    }

    public void selectKit(Player player, Kit kit){
        if(getKits().containsKey(player))
            kits.remove(player);
        kits.put(player,kit);
        for(int i = 0; i<=15; i++)
            player.sendMessage("");
        player.sendMessage(Main.header + "You selected the " + kit.getKitType().toString() + " kit.");
        kit.getDescription().forEach((s) -> player.sendMessage(Main.header + s));
    }

    public void joinGame(Player player){
        if(getPlayerTeam(player)!=null)
            return;
        player.getInventory().clear();
        player.teleport(lobby);
        selectKit(player,getArcherKit());
        Team toJoin = null;
        boolean empty = true;
        while(toJoin == null){
            if(empty){
                for(Team team : teams){
                    if(team.getPlayerCount()==0)
                        toJoin = team;
                }
                empty = false;
            }
            else{
                for(Team team : teams){
                    if(team.getPlayerCount()==team.getMaxPlayers())
                        continue;
                    toJoin = team;
                }
                break;
            }
        }
        if(toJoin == null)
            player.kickPlayer("Error: Unable to find  team");
        joinTeam(player, toJoin);
    }
    public void joinTeam(Player player, Team team){
        if(team.getPlayers().contains(player))
            return;
        if(getPlayerTeam(player)!=null)
            getPlayerTeam(player).removePlayer(player);
        team.addPlayer(player);
        Bukkit.broadcastMessage(Main.header + player.getName() + " joined the game on the " + team.getChatColor() + team.getTeamColor().toString() + " team.");
        updatePlayers();
    }
    public Team getPlayerTeam(Player player){
        for(Team team : teams)
            if(team.getPlayers().contains(player))
                return team;
        return null;
    }
    public Team getTeamByColor(int color){
        for(Team team : teams)
            if(team.getColorData()==color)
                return team;
        return null;
    }
    public List<Player> getPlayers(){
        List<Player> players = new ArrayList<>();
        for(Team team : teams){
            for(Player player : team.getPlayers()){
                players.add(player);
            }
        }
        return players;
    }

    public void updatePlayers(){
        if(getPlayers().size()==4 && getGameState()==GameState.JOINABLE){
            countdown = 30;
            beginCountdown();
        }
    }

    public void calculateScores(){
        gameBoard.setEntries(new HashMap<>());
        for(Team team : teams){
            gameBoard.addEntry(team.getScore(),team.getChatColor() + team.getTeamColor().toString());
        }
        for(Player player : getPlayers()){
            player.setScoreboard(gameBoard.toScoreboard());
        }
    }
    public void tick(){
        if(getGameState()==GameState.IN_PROGRESS){
            calculateScores();
            time++;
            getPlayers().forEach((player -> kits.get(player).getTickKit().onTick(player)));
            getPlayers().forEach((player)->player.setLevel(getMaxTime()-getTime()));
            if(time>getMaxTime())
                gameOver();
        }
    }
    public void gameOver(){
        for(Player player : getPlayers()){
            player.teleport(lobby);
            player.getInventory().clear();
        }
        for(Team team : teams){
            team.setPlayers(new ArrayList<>());
        }
        setGameState(GameState.JOINABLE);
        resetMap();
    }
    public void startGame(){
        setGameState(GameState.IN_PROGRESS);
        for(Team team : teams){
            for(Player player : team.getPlayers()){
                player.teleport(team.getSpawnPoint());
                if(!kits.containsKey(player))
                    kits.put(player,getSwordKit());
                kits.get(player).setupPlayer(player);
            }
        }
    }
    public void beginCountdown(){
        setGameState(GameState.STARTING);
        for(int i = 0; i<=countdown; i++){
            int time = countdown - i + 1;
            final int ii = i;
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),()->{
                Bukkit.broadcastMessage(Main.header + "Starting in " + (ii) + " seconds...");
            },time * 20);
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),()->{
            startGame();
        },(countdown + 1) * 20);
    }
    public void resetMap(){
        for(Block block : LocationManager.blocksFromTwoPoints(corner1,corner2)){
            block.setData((byte)0);
        }
    }
    public void removeBlocks(Team team, int count){
        int i = 1;
        Random r = new Random();
        List<Block> blocks = LocationManager.blocksFromTwoPoints(corner1,corner2);
        while(i<=count){
            Block b = blocks.get(r.nextInt(blocks.size() - 1));
            if(b.getType()==Material.STAINED_GLASS){
                if(b.getData()==team.getColorData()){
                    i++;
                    blocks.remove(b);
                    b.setData((byte)0);
                }
            }
        }

    }
    public void respawnPlayer(Player player){
        player.setHealth(20);
        int scre = 50;
        if(getPlayerTeam(player).getScore()<50){
            scre = getPlayerTeam(player).getScore();
        }
        getPlayerTeam(player).setScore(getPlayerTeam(player).getScore() - scre);
        removeBlocks(getPlayerTeam(player),scre);
        calculateScores();
        Bukkit.broadcastMessage(Main.header + getPlayerTeam(player).getChatColor() + "-" + scre);
        player.setVelocity(new Vector(0,0.25,0));
        player.teleport(getPlayerTeam(player).getSpawnPoint());
        kits.get(player).setupPlayer(player);
    }
}
