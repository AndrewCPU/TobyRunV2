package net.Andrewcpu.TobyRun.utils.npx;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;

/**
 * Created by stein on 6/26/2016.
 */
public class NPCSheep extends NPC{
    public NPCSheep(Entity entity, Interaction interaction) {
        super(entity, interaction);
    }
    public Sheep getSheep(){
        return ((Sheep)getEntity());
    }
}
