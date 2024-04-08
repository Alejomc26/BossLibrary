package io.papermc.bosslibrary.baseclasses;

import io.papermc.bosslibrary.BossLibrary;
import io.papermc.bosslibrary.interfaces.CustomBehavior;
import io.papermc.bosslibrary.interfaces.CustomEntity;
import org.bukkit.Location;
import org.bukkit.entity.ItemDisplay;

/**
 * Implementation class of custom entity and a base for other custom entities
 */
public abstract class CustomEntityImpl implements CustomEntity {

    private final ItemDisplay templateEntity;
    private CustomBehavior behavior;
    public CustomEntityImpl(Location location, CustomBehavior mainBehavior) {
        this.templateEntity = location.getWorld().spawn(location, ItemDisplay.class, (display) -> {

            mainBehavior.start();
            display.getScheduler().runAtFixedRate(BossLibrary.getInstance(), scheduledTask -> {

                mainBehavior.tick();
                this.tick();
                if (this.behavior != null) behavior.tick();

            }, () -> {

                mainBehavior.cancel();
                if (this.behavior != null) behavior.cancel();
                this.remove();

            }, 2, 1);
        });
    }

    @Override
    public Location getLocation() {
        return this.templateEntity.getLocation();
    }

    @Override
    public void teleport(Location location) {
        this.templateEntity.teleport(location);
    }

    @Override
    public void setBehavior(CustomBehavior behavior) {
        if (this.behavior != null) this.behavior.cancel();

        this.behavior = behavior;
        this.behavior.start();
    }

    @Override
    public ItemDisplay getTemplateEntity() {
        return this.templateEntity;
    }

    @Override
    public void remove() {
        this.templateEntity.remove();
    }

    protected abstract void tick();

}
