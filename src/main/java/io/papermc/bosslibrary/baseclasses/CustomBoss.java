package io.papermc.bosslibrary.baseclasses;

import io.papermc.bosslibrary.BossLibraryManager;
import io.papermc.bosslibrary.builders.BoneBuilder;
import io.papermc.bosslibrary.managers.BossBehaviorManager;
import io.papermc.bosslibrary.managers.BossHealthBarManager;
import io.papermc.bosslibrary.managers.BossHealthManager;
import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public abstract class CustomBoss {

    private final BossHealthManager healthManager = new BossHealthManager();
    private final BossBehaviorManager behaviorManager = new BossBehaviorManager();
    private final BossHealthBarManager healthBarManager;
    private final BoneBuilder centralBone;
    public final World world;
    private Player nearestPlayer;
    private boolean nearestPlayerHasBeenFound;
    public String bossName;

    public CustomBoss(Location spawnLocation, double health) {
        this.centralBone = new BoneBuilder(spawnLocation);
        this.world = this.getLocation().getWorld();
        this.healthManager.setHealth(health);
        this.healthBarManager = new BossHealthBarManager(this);

        EntityScheduler scheduler = centralBone.getDisplay().getScheduler();
        scheduler.runAtFixedRate(BossLibraryManager.getMainInstance(), scheduledTask -> update(), this::stop, 1, 1);
    }

    public Location getLocation() {
        return this.centralBone.getDisplay().getLocation();
    }

    public void teleport(Location location) {
        this.centralBone.teleport(location);
    }

    public BossHealthManager getHealthManager() {
        return this.healthManager;
    }

    public BossBehaviorManager getBehaviorManager() {
        return this.behaviorManager;
    }

    public Player getNearestPlayer() {
        //Gets the nearest player only if it hasn't been found this tick
        if (!nearestPlayerHasBeenFound) {
            double nearestDistanceSquare = Double.MAX_VALUE;

            for (Player player : world.getPlayers()) {
                double distanceSquare = player.getLocation().distanceSquared(this.getLocation());
                if (distanceSquare >= nearestDistanceSquare) {
                    continue;
                }
                nearestDistanceSquare = distanceSquare;
                this.nearestPlayer = player;
            }
        }

        return nearestPlayer;
    }

    public void update() {
        this.nearestPlayerHasBeenFound = false;
        this.behaviorManager.update();
        this.healthBarManager.update();
        this.tick();
    }

    public void stop() {
        this.centralBone.remove();
        this.behaviorManager.stop();
        this.healthBarManager.stopBossBar();
        this.death();
    }

    public abstract void tick();

    public abstract void death();

}
