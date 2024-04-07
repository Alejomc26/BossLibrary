package io.papermc.bosslibrary.singleton;

import io.papermc.bosslibrary.baseclasses.CustomHitbox;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HitboxManager {

    private final Map<UUID, CustomHitbox> hitboxList = new HashMap<>();
    private static HitboxManager instance = null;
    private HitboxManager() {}

    public static HitboxManager getInstance() {
        if (instance == null) {
            instance = new HitboxManager();
        }
        return instance;
    }

    public void registerHitbox(CustomHitbox hitbox) {
        this.hitboxList.put(hitbox.getInteractionEntity().getUniqueId(), hitbox);
    }

    public CustomHitbox getHitbox(UUID uuid) {
        return hitboxList.get(uuid);
    }

}
