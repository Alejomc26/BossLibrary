package io.github.alejomc26;

import io.github.alejomc26.behavior.BehaviorTicker;
import io.github.alejomc26.listener.BossLibraryListener;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;

public class BossLibrary extends JavaPlugin {

    private static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        this.getServer().getPluginManager().registerEvents(new BossLibraryListener(), this);
    }

    /**
     * Returns a key for the use of BossLibrary, internal use only.
     * @param key Key name.
     * @return New key.
     */
    @ApiStatus.Internal
    public static NamespacedKey createKey(String key) {
        return new NamespacedKey(instance, key);
    }

}
