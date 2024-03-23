package io.papermc.bosslibrary.schedulers;

import io.papermc.bosslibrary.BossLibraryManager;
import io.papermc.bosslibrary.custom_entities.CustomBoss;
import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import org.bukkit.Bukkit;

public abstract class BossScheduler {

    private EntityScheduler scheduler;
    public void startScheduler(CustomBoss entity) {
        if (scheduler == null) {
            this.scheduler = entity.getSlimeHitbox().getScheduler();
            this.scheduler.runAtFixedRate(BossLibraryManager.getMainInstance(), scheduledTask -> this.update(), this::stop, 1, 1);
        } else {
            Bukkit.getLogger().warning("Custom scheduler start method has run multiple times ");
        }
    }

    public abstract void update();
    public abstract void stop();
}
