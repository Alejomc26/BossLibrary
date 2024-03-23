package io.papermc.bosslibrary.custom_entities;

import io.papermc.bosslibrary.behavior.CustomBehavior;
import io.papermc.bosslibrary.boss_utils.BossHealthBar;
import io.papermc.bosslibrary.boss_utils.Hitbox;
import io.papermc.bosslibrary.schedulers.BossScheduler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;

import java.util.Comparator;

public abstract class CustomBoss extends BossScheduler {

    private final BossHealthBar bossHealthBar;
    private CustomBehavior currentBehavior;
    private final String bossName;
    private final Hitbox hitbox;
    private Player target;
    public CustomBoss(String bossName, Location spawnLocation, double health, int hitboxSize, int chargeTicks) {
        this.hitbox = new Hitbox(spawnLocation, hitboxSize);
        this.hitbox.setHealth(health);

        this.bossName = bossName;
        this.bossHealthBar = new BossHealthBar(this, chargeTicks);
        this.startScheduler(this);
    }

    public String getBossName() {
        return this.bossName;
    }

    public Player getTarget() {
        return this.target;
    }

    public Location getLocation() {
        return this.hitbox.getSlime().getLocation();
    }

    public void teleport(Location location) {
        this.hitbox.getSlime().teleport(location);
    }

    public void setBehavior(CustomBehavior behavior) {
        if (this.currentBehavior != null) {
            this.currentBehavior.exit();
        }

        this.currentBehavior = behavior;
        this.currentBehavior.start();
    }

    public Hitbox getHitbox() {
        return this.hitbox;
    }

    public Slime getSlimeHitbox() {
        return this.hitbox.getSlime();
    }

    @Override
    public void update() {
        //Sets the target
        this.target = Bukkit.getOnlinePlayers().stream()
                .min(Comparator.comparingDouble(player -> player.getLocation().distanceSquared(this.getLocation())))
                .orElse(null);

        this.bossHealthBar.updateBossBar();
        this.tick();

        if (this.currentBehavior != null && !this.hitbox.getSlime().isInvulnerable()) {
            currentBehavior.update();
        }
    }

    @Override
    public void stop() {
        if (this.currentBehavior != null)
            this.currentBehavior.exit();

        this.bossHealthBar.stopBossbar();
        this.death();
    }

    public abstract void tick();

    public abstract void death();
}
