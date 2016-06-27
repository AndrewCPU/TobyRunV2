package net.Andrewcpu.TobyRun.utils;

import net.Andrewcpu.TobyRun.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stein on 6/18/2016.
 */
public class LocationManager {
    public static void teleportPlayerToLocation(Player player, String location){
        player.teleport(loadLocation(location));
    }
    public static Location loadLocation(String configLocation){
        configLocation = configLocation.toUpperCase();
        FileConfiguration config = Main.getInstance().getConfig();
        int x = config.getInt("Server.Location." + configLocation + ".X");
        int y = config.getInt("Server.Location." + configLocation + ".Y");
        int z = config.getInt("Server.Location." + configLocation + ".Z");
        World world = Bukkit.getWorld(config.getString("Server.Location." + configLocation + ".World"));
        return new Location(world,x,y,z);
    }
    public static void saveLocation(String configLocation, Location location){
        FileConfiguration config = Main.getInstance().getConfig();
        configLocation = configLocation.toUpperCase();
        String fC = "Server.Location." + configLocation + ".";
        config.set(fC + "X", location.getBlockX());
        config.set(fC + "Y", location.getBlockY());
        config.set(fC + "Z", location.getBlockZ());
        config.set(fC + "World", location.getWorld().getName());
        Main.getInstance().saveConfig();
        Main.getInstance().reloadConfig();
    }
    public static void saveVector(String configLocation, Vector vector){
        FileConfiguration config = Main.getInstance().getConfig();
        String fC = "Server.Vector." + configLocation + ".";
        config.set(fC + "X", vector.getX());
        config.set(fC + "Y", vector.getY());
        config.set(fC + "Z", vector.getZ());
        Main.getInstance().saveConfig();
        Main.getInstance().reloadConfig();
    }
    public static Vector loadVector(String configLocation){
        FileConfiguration config = Main.getInstance().getConfig();
        String fC = "Server.Vector." + configLocation + ".";
        return new Vector(config.getDouble(fC + "X"), config.getDouble(fC + "Y"),config.getDouble(fC + "Z"));
    }
    public static List<Block> blocksFromTwoPoints(Location loc1, Location loc2)
    {
        List<Block> blocks = new ArrayList<Block>();

        int topBlockX = (loc1.getBlockX() < loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());
        int bottomBlockX = (loc1.getBlockX() > loc2.getBlockX() ? loc2.getBlockX() : loc1.getBlockX());

        int topBlockY = (loc1.getBlockY() < loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());
        int bottomBlockY = (loc1.getBlockY() > loc2.getBlockY() ? loc2.getBlockY() : loc1.getBlockY());

        int topBlockZ = (loc1.getBlockZ() < loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());
        int bottomBlockZ = (loc1.getBlockZ() > loc2.getBlockZ() ? loc2.getBlockZ() : loc1.getBlockZ());

        for(int x = bottomBlockX; x <= topBlockX; x++)
        {
            for(int z = bottomBlockZ; z <= topBlockZ; z++)
            {
                for(int y = bottomBlockY; y <= topBlockY; y++)
                {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);

                    blocks.add(block);
                }
            }
        }

        return blocks;
    }
}
