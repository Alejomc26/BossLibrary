package io.github.alejomc26.keys;

import org.bukkit.NamespacedKey;

public class Keys {

    private static final String NAMESPACE = "boss-library";
    private Keys() {}

    private static NamespacedKey create(String key) {
        return new NamespacedKey(NAMESPACE, key);
    }

    public static final NamespacedKey BOSS_MAX_HEALTH = create("boss-max-health");
    public static final NamespacedKey BOSS_HEALTH = create("boss-health");

}
