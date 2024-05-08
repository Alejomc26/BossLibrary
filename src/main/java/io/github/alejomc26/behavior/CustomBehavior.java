package io.github.alejomc26.behavior;

import org.bukkit.entity.Entity;

/**
 * This class represents a BossLibrary behavior
 */
public interface CustomBehavior {

    /**
     * Called when the behavior is applied to an {@link Entity}
     */
    default void start() {
    }

    /**
     * Called when the behavior is changed or the entity has being removed / unloaded
     */
    default void cancel() {
    }

    /**
     * Called every tick that the entity is loaded
     */
    void tick();

    /**
     * Called if the entity dies while the behavior is active
     */
    default void death() {
    }

}
