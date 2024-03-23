package io.papermc.bosslibrary.custom_entities;

import io.papermc.bosslibrary.displays.DisplayBuilder;
import io.papermc.bosslibrary.schedulers.ProjectileScheduler;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class CustomProjectile extends ProjectileScheduler {

    private final DisplayBuilder display;
    private final Location spawnLocation;
    private final double contactDamage;
    private double radius = 0.4;
    public CustomProjectile(Location start, double contactDamage) {
        this.spawnLocation = start.clone();
        this.contactDamage = contactDamage;
        this.display = new DisplayBuilder(start);
        this.atLaunch();
    }

    @Override
    public void update() {
        Location currentLocation = this.display.getLocation();
        Block block = currentLocation.getBlock();

        if (block.getType() != Material.AIR) {
            this.touchBlock();
            this.cancel();
            return;
        }

        if (currentLocation.distanceSquared(spawnLocation) > 2500) { //Distance from spawn location is greater than 50
            this.cancel();
            return;
        }

        for (Player player : currentLocation.getNearbyPlayers(radius))
            player.damage(this.contactDamage);

        this.tick();
    }

    public void modifyRadius(double radius) {
        this.radius = radius;
    }

    public DisplayBuilder getDisplay() {
        return this.display;
    }

    @Override
    public void stop() {
        this.display.remove();
    }

    public abstract void tick();

    public abstract void touchBlock();

    public abstract void atLaunch();
}
