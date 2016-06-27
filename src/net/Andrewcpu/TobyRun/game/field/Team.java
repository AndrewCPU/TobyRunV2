package net.Andrewcpu.TobyRun.game.field;

import net.Andrewcpu.TobyRun.game.field.types.TeamColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stein on 6/25/2016.
 */
public class Team {
    private int maxPlayers = 2;
    private int minPlayers = 1;
    private List<Player> players = new ArrayList<>();
    private Location spawnPoint;
    private TeamColor teamColor;
    private String naturalTeamName;
    private int score = 0;
    private int colorData;
    private ChatColor chatColor;

    public Team(Location spawnPoint, TeamColor teamColor,ChatColor chatColor) {
        this.spawnPoint = spawnPoint;
        this.teamColor = teamColor;
        this.naturalTeamName = teamColor.toString().toUpperCase();
        this.chatColor = chatColor;
        setupColorData();
    }

    public Team(Location spawnPoint, TeamColor teamColor, String naturalTeamName,ChatColor chatColor) {
        this.spawnPoint = spawnPoint;
        this.teamColor = teamColor;
        this.naturalTeamName = naturalTeamName;
        this.chatColor = chatColor;
        setupColorData();
    }

    public void setupColorData(){
        if(teamColor==TeamColor.BLACK)
            colorData = 15;
        else if(teamColor==TeamColor.RED)
            colorData = 14;
        else if(teamColor== TeamColor.GRAY)
            colorData = 8;
        else if(teamColor == TeamColor.GREEN)
            colorData = 5;
        else
            colorData = 0;
    }
    //Team Logic
    public void addPlayer(Player player){
        if(players.contains(player))
            return;
        players.add(player);
    }

    public void removePlayer(Player player){
        if(!players.contains(player))
            return;
        players.remove(player);
    }

    public int getPlayerCount(){
        return players.size();
    }

    //End Logic


    public ChatColor getChatColor() {
        return chatColor;
    }

    public void setChatColor(ChatColor chatColor) {
        this.chatColor = chatColor;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Location getSpawnPoint() {
        return spawnPoint;
    }

    public void setSpawnPoint(Location spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public TeamColor getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(TeamColor teamColor) {
        this.teamColor = teamColor;
    }

    public String getNaturalTeamName() {
        return naturalTeamName;
    }

    public void setNaturalTeamName(String naturalTeamName) {
        this.naturalTeamName = naturalTeamName;
    }

    public int getColorData() {
        return colorData;
    }

    public void setColorData(int colorData) {
        this.colorData = colorData;
    }
}
