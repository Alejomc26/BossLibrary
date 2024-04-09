package io.github.alejomc26.baseclasses;

import com.google.common.base.Preconditions;
import io.github.alejomc26.keys.Keys;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * Represents a boss from BossLibrary
 */
public final class Boss extends CustomEntityImpl {

    private final PersistentDataContainer container = this.getTemplateEntity().getPersistentDataContainer();
    private final BossBar bossBar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SOLID);
    private int immunityFrames = 10;
    private String bossName;
    public Boss(Location location, double health) {
        super(location);

        this.setMaxHealth(health);
        this.setHealth(health);
    }

    /**
     * Gets the boss current health
     * @return current health
     */
    public double getHealth() {
        return this.container.getOrDefault(Keys.BOSS_HEALTH, PersistentDataType.FLOAT, 0f);
    }

    /**
     * Set's the boss health, it can't be negative or surpass {@link #getMaxHealth()}
     * @param health new boss health
     */
    public void setHealth(double health) {
        Preconditions.checkArgument(health >= 0 && health <= this.getMaxHealth());

        this.container.set(Keys.BOSS_HEALTH, PersistentDataType.FLOAT, (float) health);
        if (this.getHealth() <= 0) {
            this.remove();
        }
    }

    /**
     * Gets the boss max health, this is also used for the boss bar
     * @return max health
     */
    public double getMaxHealth() {
        return this.container.getOrDefault(Keys.BOSS_MAX_HEALTH, PersistentDataType.FLOAT, 0f);
    }

    /**
     * Sets the boss max health, this will also affect the boss bar
     * @param maxHealth new boss max health
     */
    public void setMaxHealth(double maxHealth) {
        this.container.set(Keys.BOSS_MAX_HEALTH, PersistentDataType.FLOAT, (float) maxHealth);
    }

    /**
     * Gets the immunity frames that the boss will have after being damaged
     * @return immunity frames
     */
    public int getImmunityFrames() {
        return this.immunityFrames;
    }

    /**
     * Sets the immunity frames that the boss will have after being damaged, this is handled per hitbox
     * @param immunityFrames new immunity frames
     */
    public void setImmunityFrames(int immunityFrames) {
        this.immunityFrames = immunityFrames;
    }

    /**
     * Gets the boss name
     * @return boss name
     */
    public String getBossName() {
        return this.bossName;
    }

    /**
     * Sets the boss name, this name will also appear in the boss bar
     * @param bossName new boss name
     */
    public void setBossName(String bossName) {
        this.bossName = bossName;
        this.bossBar.setTitle(this.bossName);
    }

    /**
     * Damages the boss for the given damage
     * @param damage damage
     */
    public void damage(double damage) {
        this.setHealth(this.getHealth() - damage);
    }

    /**
     * Gets the boss bar, useful if you want to modify the style of it
     * @return boss bar
     */
    public BossBar getBossBar() {
        return bossBar;
    }

    @Override
    protected void tick() {
        bossBar.setProgress(this.getHealth() / this.getMaxHealth());
        for (Player player : this.getLocation().getWorld().getPlayers()) {
            if (bossBar.getPlayers().contains(player)) {
                continue;
            }

            bossBar.addPlayer(player);
        }
    }
}
