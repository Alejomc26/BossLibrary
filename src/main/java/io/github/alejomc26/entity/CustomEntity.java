package io.github.alejomc26.entity;

import com.google.common.base.Preconditions;
import io.github.alejomc26.BossLibrary;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;

/**
 * Helps to use an {@link ItemDisplay} as a more complex entity
 */
public class CustomEntity {

    public static final NamespacedKey BOSS_MAX_HEALTH_KEY = BossLibrary.createKey("bossMaxHealth");
    public static final NamespacedKey BOSS_HEALTH_KEY = BossLibrary.createKey("bossHealth");
    private final PersistentDataContainer container;
    private final ItemDisplay itemDisplay;

    public CustomEntity(@NotNull ItemDisplay itemDisplay) {
        this.container = itemDisplay.getPersistentDataContainer();
        this.itemDisplay = itemDisplay;
    }

    public double getMaxHealth() {
        return this.container.getOrDefault(CustomEntity.BOSS_MAX_HEALTH_KEY, PersistentDataType.DOUBLE, 0.0);
    }

    public void setMaxHealth(double health) {
        Damageable damageable;
        Preconditions.checkArgument(health > 0, "Error in setMaxHealth " + health);
        this.container.set(CustomEntity.BOSS_MAX_HEALTH_KEY, PersistentDataType.DOUBLE, health);
        if (this.getHealth() > health) {
            this.setHealth(health);
        }
    }

    public double getHealth() {
        return this.container.getOrDefault(CustomEntity.BOSS_HEALTH_KEY, PersistentDataType.DOUBLE, 0.0);
    }

    public void setHealth(double health) {
        double clampedHealth = Math.clamp(0, this.getMaxHealth(), health);
        this.container.set(CustomEntity.BOSS_HEALTH_KEY, PersistentDataType.DOUBLE, clampedHealth);
        if (this.getHealth() == 0) {
            itemDisplay.remove();
        }
    }

    public void damage(double damage) {
        this.setHealth(this.getHealth() - damage);
    }

}
