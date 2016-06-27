package net.Andrewcpu.TobyRun.utils.npx;

import net.minecraft.server.v1_10_R1.EntityCreature;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftCreature;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import sun.nio.cs.ext.PCK;

import java.util.HashMap;

/**
 * Created by stein on 6/26/2016.
 */
public class NPCManager {
    private static HashMap<Integer,NPC> npcMap = new HashMap<>();
    public static void cleanUp(){
        for(NPC npc : npcMap.values()){
            npc.getEntity().remove();
        }
    }
    public static int getNextID(){
        return npcMap.size();
    }
    public static boolean isInteractable(Entity entity){
        for(NPC npc : npcMap.values())
            if(npc.getEntity()==entity)
                return true;
        return false;
    }
    public static NPC getInteractableEntity(Entity entity){
        for(NPC npc : npcMap.values())
            if(npc.getEntity()==entity)
                return npc;
        return null;
    }
    public static NPC createNPC(NPCType type, Location location, Interaction interaction, String name){
        if(type==NPCType.SHEEP){
            NPCSheep sheep = new NPCSheep(location.getWorld().spawnEntity(location, EntityType.SHEEP), interaction);
            sheep.getEntity().teleport(location);
            sheep.getSheep().setCustomName(name);
            sheep.getSheep().setCustomNameVisible(true);
            sheep.getSheep().setAI(false);
            npcMap.put(getNextID(),sheep);
            return sheep;
        }
        else if(type==NPCType.ZOMBIE){
            NPCZombie zombie = new NPCZombie(location.getWorld().spawnEntity(location, EntityType.ZOMBIE), interaction);
            zombie.getEntity().teleport(location);
            zombie.getZombie().setCustomName(name);
            zombie.getZombie().setCustomNameVisible(true);
            zombie.getZombie().setAI(false);
            zombie.getZombie().setVillager(false);
            zombie.getZombie().setBaby(false);
            npcMap.put(getNextID(),zombie);
            return zombie;
        }
        return null;
    }
}
