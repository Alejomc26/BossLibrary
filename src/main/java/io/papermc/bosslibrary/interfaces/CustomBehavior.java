package io.papermc.bosslibrary.interfaces;

/**
 * This class represents a BossLibrary behavior
 */
public interface CustomBehavior {

    /**
     * Called when the behavior is applied, useful for declaring
     * variables
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
}
