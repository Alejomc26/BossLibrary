package io.github.alejomc26.singleton;

import io.github.alejomc26.baseclasses.Hitbox;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HitboxManager {

    private final Map<UUID, Hitbox> hitboxList = new HashMap<>();
    private static HitboxManager instance = null;
    private HitboxManager() {}

    public static HitboxManager getInstance() {
        if (instance == null) {
            instance = new HitboxManager();
        }
        return instance;
    }

    public void registerHitbox(Hitbox hitbox) {
        this.hitboxList.put(hitbox.getInteractionEntity().getUniqueId(), hitbox);
    }

    public Hitbox getHitbox(UUID uuid) {
        return hitboxList.get(uuid);
    }

}
