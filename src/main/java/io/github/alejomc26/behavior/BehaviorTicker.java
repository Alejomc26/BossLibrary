package io.github.alejomc26.behavior;

import io.papermc.paper.threadedregions.scheduler.EntityScheduler;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BehaviorTicker {

    private static final Map<UUID, BehaviorTicker> registeredEntities = new HashMap<>();
    private CustomBehavior currentBehavior;

    private BehaviorTicker(Plugin plugin, Entity entity) {
        this.startTicker(plugin, entity.getScheduler(), entity.getUniqueId());
    }

    private void startTicker(Plugin plugin, EntityScheduler entityScheduler, final UUID uuid) {
        entityScheduler.runAtFixedRate(plugin, scheduledTask -> {
            if (this.currentBehavior != null) {
                this.currentBehavior.tick();
            }
        }, () -> {
            registeredEntities.remove(uuid);
            if (this.currentBehavior != null) {
                this.currentBehavior.death();
            }
        }, 1, 1);
    }

    /**
     * Gets an instance of {@link BehaviorTicker} if the entity already has one associated,
     * otherwise creates one and returns it.
     * @param plugin The plugin that owns the task.
     * @param entity Entity that will schedule the current behavior.
     * @return Instance associated with the passed entity.
     */
    public static BehaviorTicker getOrInstantiate(@NotNull Plugin plugin, @NotNull Entity entity) {
        return registeredEntities.computeIfAbsent(entity.getUniqueId(), (ticker) -> new BehaviorTicker(plugin, entity));
    }

    /**
     * Sets the behavior that will be ticked, if the previous one wasn't null calls
     * its cancel method.
     * @param behavior New behavior.
     */
    public void setBehavior(@NotNull CustomBehavior behavior) {
        if (this.currentBehavior != null) {
            behavior.cancel();
        }
        this.currentBehavior = behavior;
        this.currentBehavior.start();
    }

}
