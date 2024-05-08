package io.github.alejomc26.listener;

import io.github.alejomc26.entity.Hit;
import io.github.alejomc26.utils.BossUtils;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class BossLibraryListener implements Listener {

    @EventHandler
    public void playerAttackHitboxListener(EntityDamageEvent event) {
        if (event.getEntity() instanceof Interaction interaction && event.getDamageSource().getCausingEntity() instanceof Player player) {
            Hit hitbox = new Hit(interaction);
            hitbox.damage(BossUtils.getPlayerDamage(player));
        }
    }

}
