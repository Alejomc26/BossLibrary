package io.github.alejomc26.behavior.common_behaviors;

import io.github.alejomc26.entity.Hit;
import io.github.alejomc26.behavior.CustomBehavior;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;

import java.util.Random;

/**
 * Base behavior for a {@link Hit} to take damage from arrows and handle its immunity ticks properly
 */
public class HitboxBaseBehavior implements CustomBehavior {

    private final Random random = new Random();
    private final Interaction interaction;
    private final Hit hitbox;

    public HitboxBaseBehavior(@NotNull Interaction interaction) {
        this.interaction = interaction;
        this.hitbox = new Hit(interaction);
    }

    @Override
    public void tick() {
        int hitboxInvulnerableTicks = this.hitbox.getInvulnerableTicks();
        if (hitboxInvulnerableTicks > 0) {
            this.hitbox.setInvulnerableTicks(hitboxInvulnerableTicks - 1);
        }

        double interactionHeight = this.interaction.getHeight();
        double interactionWidth = this.interaction.getWidth();
        for (Entity entity : this.interaction.getNearbyEntities(interactionWidth / 2, interactionHeight, interactionWidth / 2)) {
            if (!(entity instanceof AbstractArrow arrow)) {
                continue;
            }
            this.hitbox.damage(getArrowDamage(arrow));
        }
    }

    private int getArrowDamage(AbstractArrow arrow) {
        double velocityMultiplier = arrow.getVelocity().length();
        int arrowDamage = (int) Math.ceil(velocityMultiplier * arrow.getDamage());
        if (arrow.isCritical()) {
            int criticalDamage = random.nextInt(arrowDamage / 2 + 2);
            arrowDamage += criticalDamage;
        }
        return arrowDamage;
    }

}
