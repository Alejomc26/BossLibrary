package io.github.alejomc26.listener;

import io.github.alejomc26.baseclasses.Hitbox;
import io.github.alejomc26.singleton.HitboxManager;
import io.github.alejomc26.utils.BossUtils;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class BossLibraryListener implements Listener {

    @EventHandler
    public void hitboxDamageListener(EntityDamageEvent event) {
        if (event.getEntity() instanceof Interaction interaction && event.getDamageSource().getCausingEntity() instanceof Player player) {
            Hitbox hitbox = HitboxManager.getInstance().getHitbox(interaction.getUniqueId());
            if (hitbox != null && hitbox.getImmunityFrames() <= 0) {
                hitbox.getBoss().damage(BossUtils.getPlayerDamage(player));
            }
        }
    }
}
