package io.papermc.bosslibrary.builders;

import io.papermc.bosslibrary.BossLibraryManager;
import io.papermc.bosslibrary.baseclasses.CustomBoss;
import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import org.bukkit.Location;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Interaction;

import java.util.Random;

public class HitboxBuilder {
    private final Random random = new Random();
    private final Interaction interaction;
    private double width = 1;
    private double height = 1;
    public HitboxBuilder(CustomBoss boss) {
        this.interaction = boss.getLocation().getWorld().spawn(boss.getLocation(), Interaction.class);
        this.interaction.setResponsive(true);

        EntityScheduler scheduler = this.interaction.getScheduler();
        scheduler.runAtFixedRate(BossLibraryManager.getMainInstance(), scheduledTask -> {
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

                    //Removes the arrow after damaging the boss
                    arrow.remove();
                }
            }
        }, () -> {}, 1, 1);
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

    public void remove() {
        this.interaction.remove();
    }
}
