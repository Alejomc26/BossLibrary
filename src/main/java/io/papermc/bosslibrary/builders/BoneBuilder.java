package io.papermc.bosslibrary.builders;

import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BoneBuilder extends Matrix4fBuilder {

    private final ItemDisplay bone;
    public BoneBuilder(Location location) {
        this.bone = location.getWorld().spawn(location, ItemDisplay.class);
        this.bone.setTeleportDuration(2);
    }

    public ItemDisplay getDisplay() {
        return this.bone;
    }

    public void setModel(ItemStack itemStack, int customModelData) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setCustomModelData(customModelData);

        itemStack.setItemMeta(meta);
        this.bone.setItemStack(itemStack);
    }

    public void teleport(Location location) {
        this.bone.teleport(location);
    }

    public void remove() {
        this.bone.remove();
    }
}
