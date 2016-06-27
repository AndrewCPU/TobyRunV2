package net.Andrewcpu.TobyRun.utils.npx;

import org.bukkit.entity.Entity;

/**
 * Created by stein on 6/26/2016.
 */
public class NPC {
    private Entity entity;
    private Interaction interaction;

    public NPC(Entity entity, Interaction interaction) {
        this.entity = entity;
        this.interaction = interaction;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Interaction getInteraction() {
        return interaction;
    }

    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }
}
