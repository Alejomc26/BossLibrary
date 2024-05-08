package io.github.alejomc26.behavior;

import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import org.jetbrains.annotations.NotNull;
import org.bukkit.plugin.Plugin;

/**
 * Uses an {@link EntityScheduler} to tick a {@link CustomBehavior}
 */
public class BehaviorTick {

    private CustomBehavior[] behaviorList;
    public BehaviorTick(@NotNull EntityScheduler entityScheduler, @NotNull Plugin plugin) {
        entityScheduler.runAtFixedRate(plugin, scheduledTask -> {
            if (this.behaviorList != null) {
                for (CustomBehavior behavior : behaviorList) {
                    behavior.tick();
                }
            }
        }, () -> {
            if (this.behaviorList != null) {
                for (CustomBehavior behavior : behaviorList) {
                    behavior.death();
                }
            }
        }, 1, 1);
    }

    /**
     * Sets a list of {@link CustomBehavior} to tick, {@link CustomBehavior#cancel()} will be called on the previous list of {@link CustomBehavior}
     * @param behaviorList list of CustomBehaviors
     */
    public void setBehaviorList(@NotNull CustomBehavior... behaviorList) {
        if (this.behaviorList != null) {
            for (CustomBehavior behavior : behaviorList) {
                behavior.cancel();
            }
        }

        this.behaviorList = behaviorList;
        for (CustomBehavior behavior : this.behaviorList) {
            behavior.start();
        }
    }

    /**
     * Stops ticking the current list of {@link CustomBehavior} and calls {@link CustomBehavior#cancel()} on them
     */
    public void cancelCurrentBehavior() {
        if (this.behaviorList != null) {
            for (CustomBehavior behavior : this.behaviorList) {
                behavior.cancel();
            }
        }
        this.behaviorList = null;
    }

    /**
     * Stops ticking the current list of {@link CustomBehavior} without calling {@link CustomBehavior#cancel()} on them
     */
    public void removeCurrentBehavior() {
        this.behaviorList = null;
    }

}
