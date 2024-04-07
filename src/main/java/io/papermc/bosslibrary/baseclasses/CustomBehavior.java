package io.papermc.bosslibrary.baseclasses;

public interface CustomBehavior {

    /**
     * Called when the behavior starts
     */
    default void start() {
    }

    /**
     * Called when the entity dies or despawn
     */
    default void cancel() {
    }

    /**
     * Called every tick that the entity is alive / loaded
     */
    void tick();

}
