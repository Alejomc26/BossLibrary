package io.papermc.bosslibrary.baseclasses;

import io.papermc.bosslibrary.BossLibrary;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;

public abstract class BaseEntity {

    private final ItemDisplay templateEntity;
    private CustomBehavior currentBehavior;
    private ScheduledTask task;
    private final Location location;
    public BaseEntity(Location location) {
        this.templateEntity = location.getWorld().spawn(location, ItemDisplay.class, (display) ->
                task = display.getScheduler().runAtFixedRate(BossLibrary.getInstance(), scheduledTask -> {
                    //Task to execute
                    if (this.currentBehavior != null) {
                        this.currentBehavior.tick();
                    }

                    this.update();
                }, this::cancel, 1, 1));

        this.location = location;
    }

    public void setBehavior(CustomBehavior behavior) {
        if (this.currentBehavior != null)  {
            this.currentBehavior.cancel();
        }

        this.currentBehavior = behavior;
        this.currentBehavior.start();
    }

    public Location getLocation() {
        this.templateEntity.getLocation(this.location);
        return this.location;
    }

    public void teleport(Location location) {
        this.templateEntity.teleport(location);
    }

    public ItemDisplay getTemplateEntity() {
        return this.templateEntity;
    }

    public void remove() {
        this.templateEntity.remove();
        this.task.cancel();
    }

    public abstract void update();
    public abstract void cancel();
}
