package io.papermc.bosslibrary.displays;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BoneBuilder {

    private final ItemStack fakeDisplay = new ItemStack(Material.DIAMOND_SWORD);
    private final ItemDisplay bone;
    public BoneBuilder(Location location) {
        this.bone = location.getWorld().spawn(location, ItemDisplay.class);
        this.bone.setTeleportDuration(2);
        this.bone.setItemStack(fakeDisplay);
    }

    public void setCustomModelData(int data) {
        ItemMeta meta = this.fakeDisplay.getItemMeta();
        meta.setCustomModelData(data);

        this.fakeDisplay.setItemMeta(meta);
    }

    public void teleport(Location location) {
        this.bone.teleport(location);
    }

    public Location getLocation() {
        return this.bone.getLocation();
    }
}
