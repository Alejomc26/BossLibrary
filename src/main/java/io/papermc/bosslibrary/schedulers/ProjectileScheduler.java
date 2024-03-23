package io.papermc.bosslibrary.schedulers;

import io.papermc.bosslibrary.BossLibraryManager;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;

public abstract class ProjectileScheduler {

    private final ScheduledTask task;
    public ProjectileScheduler() {
        GlobalRegionScheduler globalScheduler = Bukkit.getGlobalRegionScheduler();
        this.task = globalScheduler.runAtFixedRate(BossLibraryManager.getMainInstance(), scheduledTask -> this.update(), 1, 1);
    }

    public void cancel() {
        if (task != null) {
            this.stop();
            this.task.cancel();
        }
    }

    public abstract void stop();

    public abstract void update();

}
