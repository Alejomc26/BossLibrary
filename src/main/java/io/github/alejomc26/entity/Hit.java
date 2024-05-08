package io.github.alejomc26.entity;

import io.github.alejomc26.BossLibrary;
import io.github.alejomc26.data_types.UUIDDataType;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * Helps to use an {@link Interaction} as a hitbox for a {@link CustomEntity}
 */
public class Hit {

    public static final NamespacedKey HITBOX_INVULNERABLE_TICKS = BossLibrary.createKey("hitboxInvulnerableTicks");
    public static final NamespacedKey HITBOX_DAMAGE_REDUCTION = BossLibrary.createKey("hitboxDamageReduction");
    public static final NamespacedKey HITBOX_OWNER_KEY = BossLibrary.createKey("hitboxOwner");
    private final PersistentDataContainer container;

    public Hit(@NotNull Interaction interaction) {
        this.container = interaction.getPersistentDataContainer();
    }

    /**
     * @return remaining ticks the hitbox will be invulnerable for
     */
    public int getInvulnerableTicks() {
        return this.container.getOrDefault(Hit.HITBOX_INVULNERABLE_TICKS, PersistentDataType.INTEGER, 0);
    }

    /**
     * Make the hitbox invulnerable for a number of ticks
     * @param immunityTicks ticks the hitbox will be invulnerable for
     */
    public void setInvulnerableTicks(int immunityTicks) {
        container.set(Hit.HITBOX_INVULNERABLE_TICKS, PersistentDataType.INTEGER, immunityTicks);
    }

    /**
     * @return percentage of damage that this hitbox will reduce from {@link Hit#damage(double)}
     */
    public double getDamageReduction() {
        return this.container.getOrDefault(Hit.HITBOX_DAMAGE_REDUCTION, PersistentDataType.DOUBLE, 0.0);
    }

    /**
     * @param damageReduction percentage of damage that this hitbox will reduce from {@link Hit#damage(double)}
     */
    public void setDamageReduction(double damageReduction) {
        this.container.set(Hit.HITBOX_DAMAGE_REDUCTION, PersistentDataType.DOUBLE, damageReduction);
    }

    /**
     * Gets the {@link ItemDisplay} that will receive damage via {@link CustomEntity}
     * @return Hitbox owner or null if it hasn't been set yet
     */
    @Nullable
    public ItemDisplay getHitboxOwner() {
        if (!this.container.has(Hit.HITBOX_OWNER_KEY, new UUIDDataType())) {
            return null;
        }
        UUID hitboxOwnerUUID = container.get(Hit.HITBOX_OWNER_KEY, new UUIDDataType());
        if (hitboxOwnerUUID == null) {
            return null;
        }
        return (ItemDisplay) Bukkit.getEntity(hitboxOwnerUUID);
    }

    /**
     * Sets the {@link ItemDisplay} that will receive damage via {@link CustomEntity}
     * @param itemDisplay will receive damage from the hitbox
     */
    public void setHitboxOwner(ItemDisplay itemDisplay) {
        container.set(Hit.HITBOX_OWNER_KEY, new UUIDDataType(), itemDisplay.getUniqueId());
    }

    /**
     * Damages the hitbox owner if it isn't null, damage is reduced depending on damage reduction
     * @param damage damage that the owner will receive before damage reduction is applied
     */
    public void damage(double damage) {
        if (this.getInvulnerableTicks() > 0) {
            return;
        }
        ItemDisplay hitboxOwner = getHitboxOwner();
        if (hitboxOwner == null) {
            return;
        }
        double damageMultiplier = 1 - (this.getDamageReduction() / 100);
        double finalDamage = damage * damageMultiplier;
        DamageableDisplay.damage(hitboxOwner, finalDamage);
    }

}
