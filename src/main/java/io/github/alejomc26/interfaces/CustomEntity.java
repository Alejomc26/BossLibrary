package io.github.alejomc26.interfaces;

import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;

/**
 * This class represents a custom entity from BossLibrary
 */
public interface CustomEntity {

    /**
     * Gets a copy of the actual location of the entity
     * @return location
     */
    Location getLocation();

    /**
     * Teleport the entity to a specific location
     * @param location Location to teleport
     */
    void teleport(Location location);

    /**
     * Sets the behavior of the entity, this will override a previous one
     * if the entity had one
     * @param behavior New behavior
     */
    void setBehavior(CustomBehavior behavior);

    /**
     * Gets the template entity of the custom entity, this entity
     * is an ItemStack
     * @return Template entity
     */
    ItemDisplay getTemplateEntity();

    /**
     * Removes the entity
     */
    void remove();
}
