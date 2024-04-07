package io.papermc.bosslibrary.listener;

import io.papermc.bosslibrary.baseclasses.CustomHitbox;
import io.papermc.bosslibrary.singleton.HitboxManager;
import io.papermc.bosslibrary.utils.BossUtils;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void hitboxDamageListener(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Interaction interaction && event.getDamager() instanceof Player player) {
            CustomHitbox hitbox = HitboxManager.getInstance().getHitbox(interaction.getUniqueId());
            if (hitbox != null && hitbox.getIframes() <= 0) {
                hitbox.getBoss().damage(BossUtils.getPlayerDamage(player));
            }
        }
    }
}
