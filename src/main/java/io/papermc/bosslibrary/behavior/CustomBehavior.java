package io.papermc.bosslibrary.behavior;

import io.papermc.bosslibrary.custom_entities.CustomBoss;

public abstract class CustomBehavior {

    private final CustomBoss boss;

    public CustomBehavior(CustomBoss boss) {
        this.boss = boss;
    }

    public CustomBoss getBoss() {
        return this.boss;
    }

    public void start() {
    }

    public void exit() {
    }

    public abstract void update();

}
