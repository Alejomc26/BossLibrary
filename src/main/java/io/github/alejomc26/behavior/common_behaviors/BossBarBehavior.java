package io.github.alejomc26.behavior.common_behaviors;

import io.github.alejomc26.behavior.CustomBehavior;
import io.github.alejomc26.entity.CustomEntity;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;

/**
 * Behavior to display a BossBar displaying the health of the {@link CustomEntity} of an {@link ItemDisplay}
 */
public class BossBarBehavior implements CustomBehavior {

    private final CustomEntity customEntity;
    private final BossBar bossBar;
    private final World world;

    public BossBarBehavior(ItemDisplay itemDisplay, BossBar bossBar) {
        this.customEntity = new CustomEntity(itemDisplay);
        this.world = itemDisplay.getWorld();
        this.bossBar = bossBar;
    }

    @Override
    public void tick() {
        this.bossBar.setProgress(this.customEntity.getHealth() / this.customEntity.getMaxHealth());
        for (Player player : world.getPlayers()) {
            if (this.bossBar.getPlayers().contains(player)) {
                continue;
            }
            bossBar.addPlayer(player);
        }
    }

    @Override
    public void cancel() {
        this.bossBar.removeAll();
    }

    @Override
    public void death() {
        this.bossBar.removeAll();
    }

}
