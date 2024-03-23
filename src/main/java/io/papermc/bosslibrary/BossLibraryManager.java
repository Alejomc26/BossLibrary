package io.papermc.bosslibrary;

import org.bukkit.plugin.java.JavaPlugin;

public class BossLibraryManager {

    private static JavaPlugin mainInstance;

    public static JavaPlugin getMainInstance() {
        return mainInstance;
    }

    public static void setMainInstance(final JavaPlugin plugin) {
        BossLibraryManager.mainInstance = plugin;
    }
}
