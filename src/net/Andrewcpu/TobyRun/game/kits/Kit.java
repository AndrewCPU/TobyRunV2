package net.Andrewcpu.TobyRun.game.kits;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stein on 6/26/2016.
 */
public class Kit {
    private KitType kitType;
    private KitContents kitContents;
    private TickKit tickKit;
    private List<String> description = new ArrayList<>();

    public Kit(KitType kitType, KitContents kitContents, TickKit tickKit) {
        this.kitType = kitType;
        this.kitContents = kitContents;
        this.tickKit = tickKit;
    }

    public KitType getKitType() {
        return kitType;
    }

    public void setKitType(KitType kitType) {
        this.kitType = kitType;
    }

    public KitContents getKitContents() {
        return kitContents;
    }

    public void setKitContents(KitContents kitContents) {
        this.kitContents = kitContents;
    }

    public TickKit getTickKit() {
        return tickKit;
    }

    public void setTickKit(TickKit tickKit) {
        this.tickKit = tickKit;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public void setupPlayer(Player player){
        player.getInventory().clear();
        getKitContents().getItemStacks().forEach(player.getInventory()::addItem);
        player.setHealth(20);
        player.setFoodLevel(20);
    }

    public void addLine(String s){
        description.add(s);
    }
}
