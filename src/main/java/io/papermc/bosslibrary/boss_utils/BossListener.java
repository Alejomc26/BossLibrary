package io.papermc.bosslibrary.boss_utils;

import io.papermc.bosslibrary.utils.BossKeys;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.persistence.PersistentDataContainer;

public class BossListener implements Listener {

    @EventHandler
    public void slimeSplitHandler(SlimeSplitEvent event) {
        Slime slime = event.getEntity();
        PersistentDataContainer container = slime.getPersistentDataContainer();
        if (container.has(BossKeys.slime_split_key))
            event.setCancelled(true);
    }

    @EventHandler
    public void fallDamageHandler(EntityDamageEvent event) {
        if (event.getEntity() instanceof Slime slime)
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL)
                if (slime.getPersistentDataContainer().has(BossKeys.slime_split_key))
                    event.setCancelled(true);
    }
}
