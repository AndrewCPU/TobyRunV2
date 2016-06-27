package net.Andrewcpu.TobyRun.game.kits;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stein on 6/26/2016.
 */
public class KitContents {
     private List<ItemStack> itemStacks = new ArrayList<>();
    public KitContents(){

    }

    public KitContents(List<ItemStack> itemStacks) {
        this.itemStacks = itemStacks;
    }

    public void addItem(ItemStack itemStack){
        itemStacks.add(itemStack);
    }
    public void removeItem(ItemStack itemStack){
        itemStacks.remove(itemStack);
    }

    public List<ItemStack> getItemStacks() {
        return itemStacks;
    }

    public void setItemStacks(List<ItemStack> itemStacks) {
        this.itemStacks = itemStacks;
    }
}
