package io.github.alejomc26.builders;

import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

/**
 * Builder to make the process of making an {@link ItemDisplay} with a custom model easier
 */
public class ModelBuilder {

    private final ItemStack itemStack;
    private Location location;

    public ModelBuilder(@NotNull ItemStack itemStack) {
        this.itemStack = itemStack.clone();
    }

    /**
     * Sets a custom model data to the item from the constructor
     * @param customModelData custom model data to be applied
     * @return a reference to this object
     */
    public ModelBuilder setCustomModelData(int customModelData) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setCustomModelData(customModelData);
        itemStack.setItemMeta(meta);
        return this;
    }

    /**
     * Sets the spawn location for the model
     * @param location spawn location
     * @return a reference to this object
     */
    public ModelBuilder setSpawnLocation(@NotNull Location location) {
        this.location = location.clone();
        return this;
    }

    /**
     * Spawn an item display with the specified config
     * @return item display with the model
     */
    public ItemDisplay spawn() {
        if (location == null) {
            throw new IllegalStateException("Spawn location not defined");
        }
        return location.getWorld().spawn(location, ItemDisplay.class, (display) -> {
            display.setItemStack(itemStack.clone());
            display.setTeleportDuration(2);
        });
    }
}
