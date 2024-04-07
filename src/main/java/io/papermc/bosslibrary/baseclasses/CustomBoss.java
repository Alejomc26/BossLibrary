package io.papermc.bosslibrary.baseclasses;

import com.google.common.base.Preconditions;
import io.papermc.bosslibrary.keys.Keys;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public abstract class CustomBoss extends BaseEntity {

    private final PersistentDataContainer container = this.getTemplateEntity().getPersistentDataContainer();
    private final BossBar bossBar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SOLID);;
    private String bossName;
    public CustomBoss(Location location, double health) {
        super(location);

        this.bossBar.addFlag(BarFlag.DARKEN_SKY);
        this.setMaxHealth(health);
        this.setHealth(health);
    }

    public float getMaxHealth() {
        return this.container.getOrDefault(Keys.BOSS_MAX_HEALTH, PersistentDataType.FLOAT, 0f);
    }

    public float getHealth() {
        return this.container.getOrDefault(Keys.BOSS_HEALTH, PersistentDataType.FLOAT, 0f);
    }

    public void setMaxHealth(double health) {
        this.container.set(Keys.BOSS_MAX_HEALTH, PersistentDataType.FLOAT, (float) health);
    }

    public void setHealth(double health) {
        Preconditions.checkArgument(health >= 0 && health <= this.getMaxHealth());

        this.container.set(Keys.BOSS_HEALTH, PersistentDataType.FLOAT, (float) health);
        if (this.getHealth() <= 0) {
            this.remove();
        }
    }

    public void damage(double damage) {
        this.setHealth(this.getHealth() - damage);
    }

    public String getBossBarName() {
        return this.bossName;
    }

    public void setBossBarName(String bossBarName) {
        this.bossName = bossBarName;
        this.bossBar.setTitle(this.bossName);
    }

    public BossBar getBossBar() {
        return this.bossBar;
    }

    @Override
    public void update() {
        BossBar bossBar = this.bossBar;
        bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        for (Player player : this.getLocation().getWorld().getPlayers()) {
            if (bossBar.getPlayers().contains(player)) {
                continue;
            }

            bossBar.addPlayer(player);
            this.tick();
        }
    }

    @Override
    public void remove() {
        this.bossBar.removeAll();
        super.remove();
    }

    public abstract void tick();

}
