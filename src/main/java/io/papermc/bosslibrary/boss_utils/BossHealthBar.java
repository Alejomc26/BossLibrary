package io.papermc.bosslibrary.boss_utils;

import io.papermc.bosslibrary.custom_entities.CustomBoss;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossHealthBar {

    private final CustomBoss boss;
    private BossBar bossBar;
    private final int chargeTicks;
    private int currentTick;
    public BossHealthBar(CustomBoss boss, int chargeTicks) {
        this.chargeTicks = chargeTicks;
        this.currentTick = chargeTicks;
        this.boss = boss;

        this.bossBar = Bukkit.createBossBar(boss.getBossName(), BarColor.RED, BarStyle.SOLID);
        this.bossBar.addFlag(BarFlag.DARKEN_SKY);
    }

    public void updateBossBar() {
        if (currentTick >= 0) {
            this.bossBar.setProgress(1 - (float) (currentTick--) / chargeTicks);
        } else {
            this.bossBar.setProgress(this.boss.getSlimeHitbox().getHealth() / this.boss.getHitbox().getMaxHealth());
        }

        for (Player player : Bukkit.getOnlinePlayers())
            if (!this.bossBar.getPlayers().contains(player))
                this.bossBar.addPlayer(player);
    }

    public void stopBossbar() {
        this.bossBar.removeAll();
    }
}
