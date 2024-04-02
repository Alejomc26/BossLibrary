package io.papermc.bosslibrary.managers;

public class BossHealthManager {

    public double maxHealth = 1;
    private double health = 1;

    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    public double getMaxHealth() {
        return maxHealth;
    }
    public void setHealth(double health) {
        this.health = health;
    }

    public void damage(double damage) {
        this.health = health - damage;
    }

    public double getHealth() {
        return health;
    }

}
