package io.papermc.bosslibrary.baseclasses;

import io.papermc.bosslibrary.BossLibraryManager;
import io.papermc.bosslibrary.builders.DisplayBuilder;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public abstract class CustomProjectile{

    private final DisplayBuilder display;
    private final Location spawnLocation;
    private final double contactDamage;
    private double radius = 0.4;
    private final ScheduledTask task;

    public CustomProjectile(Location start, double contactDamage) {
        GlobalRegionScheduler globalScheduler = Bukkit.getGlobalRegionScheduler();
        this.task = globalScheduler.runAtFixedRate(BossLibraryManager.getMainInstance(), scheduledTask -> this.update(), 1, 1);
        this.spawnLocation = start.clone();
        this.contactDamage = contactDamage;
        this.display = new DisplayBuilder(start);
        this.atLaunch();
    }

    public void update() {
        Location currentLocation = this.display.getLocation();
        Block block = currentLocation.getBlock();

        if (block.getType() != Material.AIR) {
            this.touchBlock();
            this.stop();
            this.task.cancel();
            return;
        }

        if (currentLocation.distanceSquared(spawnLocation) > 2500) { //Distance from spawn location is greater than 50
            this.stop();
            this.task.cancel();
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

    public void stop() {
        this.display.remove();
    }

    public abstract void tick();

    public abstract void touchBlock();

    public abstract void atLaunch();
}
