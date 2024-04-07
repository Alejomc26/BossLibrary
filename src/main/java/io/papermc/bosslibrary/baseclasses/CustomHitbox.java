package io.papermc.bosslibrary.baseclasses;

import io.papermc.bosslibrary.singleton.HitboxManager;
import org.bukkit.Location;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;

import java.util.Random;

public class CustomHitbox extends BaseEntity {

    private final Random random = new Random();
    private final Interaction interaction;
    private CustomBoss boss;
    private double height = 1;
    private double width = 1;
    private int iframes;
    public CustomHitbox(Location location) {
        super(location);

        this.interaction = location.getWorld().spawn(location, Interaction.class, (interaction -> interaction.setResponsive(true)));
        HitboxManager.getInstance().registerHitbox(this);
    }

    public void setBoss(CustomBoss boss) {
        this.boss = boss;
    }

    public CustomBoss getBoss() {
        return this.boss;
    }

    @Override
    public void update() {
        //Reduce invulnerability frames
        if (iframes > 0) --this.iframes;

        for (Entity entity : interaction.getNearbyEntities(width / 2, height, width / 2)) {
            if (entity instanceof AbstractArrow arrow) {
                //Calculate arrow damage based on motion multiplier
                float motionMultiplier = (float) arrow.getVelocity().length();
                int arrowDamage = (int) Math.ceil(motionMultiplier * arrow.getDamage());

                //Increases arrow damage if it is critical
                if (arrow.isCritical()) {
                    long criticalMultiplier = random.nextInt(arrowDamage / 2 + 2);
                    arrowDamage = (int) (arrowDamage + criticalMultiplier);
                }
                //Damages the boss
                this.boss.damage(arrowDamage);

                //Removes the arrow after damaging the boss
                arrow.remove();
            }
        }
    }

    public Interaction getInteractionEntity() {
        return this.interaction;
    }

    public int getIframes() {
        return this.iframes;
    }

    public void setIframes(int iframes) {
        this.iframes = iframes;
    }

    public void teleport(Location location) {
        this.interaction.teleport(location);
    }

    public void setHeight(float height) {
        this.interaction.setInteractionHeight(height);
        this.height = height;
    }

    public void setWidth(float width) {
        this.interaction.setInteractionWidth(width);
        this.width = width;
    }

    @Override
    public void cancel() {
        this.interaction.remove();
    }

}
