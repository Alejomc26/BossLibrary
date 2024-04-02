package io.papermc.bosslibrary.managers;

import io.papermc.bosslibrary.baseclasses.CustomBehavior;

public class BossBehaviorManager {

    private CustomBehavior currentBehavior;

    public void setBehavior(CustomBehavior behavior) {
        if (this.currentBehavior != null) {
            this.currentBehavior.exit();
        }

        this.currentBehavior = behavior;
        this.currentBehavior.start();
    }

    public void update() {
        if (this.currentBehavior != null) {
            this.currentBehavior.tick();
        }
    }

    public void stop() {
        if (currentBehavior != null) {
            currentBehavior.exit();
        }
    }
}
