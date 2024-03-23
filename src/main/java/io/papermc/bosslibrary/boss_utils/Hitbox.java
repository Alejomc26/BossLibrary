package io.papermc.bosslibrary.boss_utils;

import io.papermc.bosslibrary.utils.BossKeys;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Slime;
import org.bukkit.persistence.PersistentDataType;

public class Hitbox {

    private final Slime slime;
    public Hitbox(Location spawnLocation, int size) {
        this.slime = spawnLocation.getWorld().spawn(spawnLocation, Slime.class);
        this.slime.getPersistentDataContainer().set(BossKeys.slime_split_key, PersistentDataType.BOOLEAN, true);
        this.slime.setPersistent(false);
        this.slime.setSize(size);
    }

    public void setHealth(double health) {
        AttributeInstance healthInstance = this.slime.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (healthInstance != null) {
            healthInstance.setBaseValue(health);
            this.slime.setHealth(health);
        }
    }

    public double getMaxHealth() {
        AttributeInstance attribute = slime.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        return attribute != null ? attribute.getBaseValue() : 1;
    }

    public Slime getSlime() {
        return slime;
    }
}