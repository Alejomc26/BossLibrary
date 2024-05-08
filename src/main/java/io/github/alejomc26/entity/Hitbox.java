package io.github.alejomc26.entity;

import com.google.common.base.Preconditions;
import io.github.alejomc26.BossLibrary;
import org.bukkit.entity.Display;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Interaction;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

/**
 * Helps to use {@link Interaction} as a hitbox for {@link Display} using {@link DamageableDisplay}.
 */
public class Hitbox {

    private static final NamespacedKey HITBOX_INVULNERABLE_TICKS = BossLibrary.createKey("hitboxInvulnerableTicks");
    private static final NamespacedKey HITBOX_DAMAGE_REDUCTION = BossLibrary.createKey("hitboxDamageReduction");
    private static final NamespacedKey HITBOX_OWNER_KEY = BossLibrary.createKey("hitboxOwner");

    /**
     * Gets the remaining invulnerable ticks stored within the pdc of the passed interaction entity.
     * @param interaction Its pdc will be used to get the invulnerable ticks.
     * @return Remaining ticks the interaction entity will be immune to damage.
     */
    public static int getInvulnerableTicks(@NotNull Interaction interaction) {
        PersistentDataContainer container = interaction.getPersistentDataContainer();
        return container.getOrDefault(Hitbox.HITBOX_INVULNERABLE_TICKS, PersistentDataType.INTEGER, 0);
    }

    /**
     * Sets a number of ticks that the passed interaction will be immune to damage.
     * @param interaction Its pdc will be used to storage the invulnerable ticks.
     * @param invulnerableTicks ticks of invulnerability.
     * @throws IllegalArgumentException Thrown if the passed invulnerableTicks is < 0.
     */
    public static void setInvulnerableTicks(@NotNull Interaction interaction, int invulnerableTicks) {
        Preconditions.checkArgument(invulnerableTicks >= 0, "Error in setInvulnerableTicks " + invulnerableTicks);
        PersistentDataContainer container = interaction.getPersistentDataContainer();
        container.set(Hitbox.HITBOX_INVULNERABLE_TICKS, PersistentDataType.INTEGER, invulnerableTicks);
    }

    /**
     * Gets the percentage of damage that this interaction will reduce when being damaged.
     * @param interaction Its pdc will be used to storage the damage reduction.
     * @return Damage reduction of the interaction.
     */
    public static int getDamageReduction(@NotNull Interaction interaction) {
        PersistentDataContainer container = interaction.getPersistentDataContainer();
        return container.getOrDefault(Hitbox.HITBOX_DAMAGE_REDUCTION, PersistentDataType.INTEGER, 0);
    }

    /**
     * Sets the percentage of damage that this interaction will reduce when being damaged.
     * @param interaction Its pdc will be used to storage the damage reduction.
     * @param damageReduction Damage reduction of the interaction.
     * @throws IllegalArgumentException Thrown if the passed damageReduction is < 0.
     */
    public static void setDamageReduction(@NotNull Interaction interaction, int damageReduction) {
        Preconditions.checkArgument(damageReduction >= 0, "Error in setDamageReduction " + damageReduction);
        PersistentDataContainer container = interaction.getPersistentDataContainer();
        container.set(Hitbox.HITBOX_DAMAGE_REDUCTION, PersistentDataType.INTEGER, damageReduction);
    }

}
