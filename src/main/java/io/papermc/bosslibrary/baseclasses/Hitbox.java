package io.papermc.bosslibrary.baseclasses;

import org.bukkit.Location;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;

import java.util.Random;

/**
 * Represents a BossLibrary hitbox
 */
public final class Hitbox extends CustomEntityImpl {

    private final Random random = new Random();
    private final Interaction interactionEntity;
    private final Boss boss;
    private int immunityFrames;
    private double damageReduction = 0;
    public Hitbox(Location location, Boss boss) {
        super(location, null);

        this.interactionEntity = location.getWorld().spawn(location, Interaction.class, (interaction) -> interaction.setResponsive(true));
        this.boss = boss;

        this.immunityFrames = boss.getImmunityFrames();
    }

    /**
     * Gets the boss of this hitbox
     * @return boss
     */
    public Boss getBoss() {
        return boss;
    }

    /**
     * Gets the interaction entity used to make the hitbox work
     * @return interaction entity
     */
    public Interaction getInteractionEntity() {
        return interactionEntity;
    }

    /**
     * Gets the immunity frames of the hitbox
     * @return immunity frames
     */
    public int getImmunityFrames() {
        return immunityFrames;
    }

    /**
     * Sets the immunity frames of the hitbox, this overrides the boss
     * immunity frames for this hitbox only
     * @param immunityFrames new immunity frames
     */
    public void setImmunityFrames(int immunityFrames) {
        this.immunityFrames = immunityFrames;
    }

    /**
     * Gets the height of this hitbox
     * @return height
     */
    public double getHeight() {
        return this.interactionEntity.getHeight();
    }

    /**
     * Sets the height of this hitbox
     * @param height new height
     */
    public void setHeight(float height) {
        this.interactionEntity.setInteractionHeight(height);
    }

    /**
     * Gets the width of this hitbox
     * @return width
     */
    public double getWidth() {
        return this.interactionEntity.getWidth();
    }

    /**
     * Sets the width of this hitbox
     * @param width width
     */
    public void setWidth(float width) {
        this.interactionEntity.setInteractionWidth(width);
    }

    /**
     * Gets the damage reduction of this hitbox
     * @return damage reduction
     */
    public double getDamageReduction() {
        return this.damageReduction;
    }

    /**
     * Sets the damage reduction of this hitbox, it is of 0 by default
     * @param damageReduction damage reduction
     */
    public void setDamageReduction(double damageReduction) {
        this.damageReduction = damageReduction;
    }

    @Override
    protected void tick() {
        if (immunityFrames > 0) --immunityFrames;

        for (Entity entity : interactionEntity.getNearbyEntities(getWidth() / 2, getHeight(), getWidth() / 2)) {
            if (entity instanceof AbstractArrow arrow) {
                float motionMultiplier = (float) arrow.getVelocity().length();
                int arrowDamage = (int) Math.ceil(motionMultiplier * arrow.getDamage());

                if (arrow.isCritical()) {
                    long criticalMultiplier = random.nextInt(arrowDamage / 2 + 2);
                    arrowDamage += criticalMultiplier;
                }

                this.boss.damage(arrowDamage);
                arrow.remove();
            }
        }
    }
}
