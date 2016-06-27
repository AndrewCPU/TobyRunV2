package net.Andrewcpu.TobyRun.game.kits;

import net.Andrewcpu.TobyRun.Main;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.potion.PotionEffect;

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
    private int maxHealth = 20;
    private List<PotionEffect> potionEffects = new ArrayList<>();
    private float walkSpeed = 1;

    public Kit(KitType kitType, KitContents kitContents, TickKit tickKit) {
        this.kitType = kitType;
        this.kitContents = kitContents;
        this.tickKit = tickKit;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void addPotion(PotionEffect effect) {
        potionEffects.add(effect);
    }

    public List<PotionEffect> getPotionEffects() {
        return potionEffects;
    }

    public void setPotionEffects(List<PotionEffect> potionEffects) {
        this.potionEffects = potionEffects;
    }

    public float getWalkSpeed() {
        return walkSpeed;
    }

    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
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
        player.setHealth(player.getMaxHealth());
        player.setFoodLevel(20);
        ItemStack banner = new ItemStack(Material.BANNER,1);
        BannerMeta bannerMeta = (BannerMeta)banner.getItemMeta();
        bannerMeta.setBaseColor(Main.getInstance().getArena().getPlayerTeam(player).getDyeColor());
        banner.setItemMeta(bannerMeta);
        player.getInventory().setHelmet(banner);
    }

    public void addLine(String s){
        description.add(s);
    }
}
