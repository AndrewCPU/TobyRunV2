package net.Andrewcpu.TobyRun.utils.npx;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;

/**
 * Created by stein on 6/26/2016.
 */
public class NPCZombie extends NPC{
    public NPCZombie(Entity entity, Interaction interaction) {
        super(entity, interaction);
    }
    public Zombie getZombie(){
        return ((Zombie)getEntity());
    }
}
