package io.papermc.bosslibrary.managers;

import io.papermc.bosslibrary.baseclasses.CustomBoss;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossHealthBarManager {

    private final BossBar bossBar;
    private final CustomBoss customBoss;
    public BossHealthBarManager(CustomBoss customB) {
        this.bossBar = Bukkit.createBossBar(customB.bossName, BarColor.RED, BarStyle.SOLID);
        this.bossBar.addFlag(BarFlag.DARKEN_SKY);
        this.customBoss = customB;
    }

    public void update() {
        BossHealthManager healthManager = this.customBoss.getHealthManager();
        this.bossBar.setProgress(healthManager.getHealth() / healthManager.getMaxHealth());

        for (Player player : this.customBoss.world.getPlayers()) {
            if (this.bossBar.getPlayers().contains(player)) {
                continue;
            }

            this.bossBar.addPlayer(player);
        }
    }

    public void stopBossBar() {
        this.bossBar.removeAll();
    }
}
