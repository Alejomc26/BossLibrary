package io.github.alejomc26.entity;

import com.google.common.base.Preconditions;
import io.github.alejomc26.BossLibrary;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Display;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

/**
 * Helps to use {@link Display} almost as a {@link Damageable} entity.
 */
public class DamageableDisplay {

    private static final NamespacedKey DISPLAY_MAX_HEALTH = BossLibrary.createKey("displayMaxHealth");
    private static final NamespacedKey DISPLAY_HEALTH = BossLibrary.createKey("displayHealth");

    /**
     * Gets the max health that has been stored within the pdc of the passed display.
     * @param display Its pdc will be used to get the maximum health.
     * @return Max health set in {@link #setMaxHealth(Display, double)} for this display, or 0 if it hasn't been set.
     */
    public static double getMaxHealth(@NotNull Display display) {
        PersistentDataContainer container = display.getPersistentDataContainer();
        return container.getOrDefault(DamageableDisplay.DISPLAY_MAX_HEALTH, PersistentDataType.DOUBLE, 0.0);
    }

    /**
     * Sets the max health within the pdc of the passed display.
     * @param display Its pdc will be used to store the maximum health.
     * @param health Max health that will be stored inside the pdc.
     * @throws IllegalArgumentException Thrown if the passed health is <= 0.
     */
    public static void setMaxHealth(@NotNull Display display, double health) {
        Preconditions.checkArgument(health > 0, "Error in setMaxHealth " + health);
        PersistentDataContainer container = display.getPersistentDataContainer();
        container.set(DamageableDisplay.DISPLAY_MAX_HEALTH, PersistentDataType.DOUBLE, health);
    }

    /**
     * Gets the health that has been stored within the pdc of the passed display.
     * @param display Its pdc will be used to get the health.
     * @return Health set in {@link #setHealth(Display, double)} for this display, or 0 if it hasn't been set.
     */
    public static double getHealth(@NotNull Display display) {
        PersistentDataContainer container = display.getPersistentDataContainer();
        return container.getOrDefault(DamageableDisplay.DISPLAY_HEALTH, PersistentDataType.DOUBLE, 0.0);
    }

    /**
     * Sets the max health within the pdc of the passed display.
     * @param display Its pdc will be used to store the health.
     * @param health Health that will be stored inside the pdc.
     * @throws IllegalArgumentException Thrown if the passed health is < 0 > {@link #getMaxHealth(Display)}.
     */
    public static void setHealth(@NotNull Display display, double health) {
        Preconditions.checkArgument(health >= 0 && health <= getMaxHealth(display), "Error in setHealth " + health);
        PersistentDataContainer container = display.getPersistentDataContainer();
        container.set(DamageableDisplay.DISPLAY_HEALTH, PersistentDataType.DOUBLE, health);
        if (health == 0) {
            display.remove();
        }
    }

    /**
     * Damages a display reducing its health and killing it if its health after the damage is <= to 0.
     * @param display Its pdc will be used to take its current health.
     * @param damage Damage that will be done to the display.
     * @throws IllegalArgumentException Thrown if the passed health is < 0.
     */
    public static void damage(@NotNull Display display, double damage) {
        Preconditions.checkArgument(damage >= 0, "Error in damage " + damage);
        double healthAfterDamage = DamageableDisplay.getHealth(display) - damage;
        if (healthAfterDamage <= 0) {
            display.remove();
        } else {
            DamageableDisplay.setHealth(display, healthAfterDamage);
        }
    }

}
