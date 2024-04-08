package io.papermc.bosslibrary;

import io.papermc.bosslibrary.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public class BossLibrary extends JavaPlugin {

    private static BossLibrary instance;
    @Override
    public void onEnable() {
        instance = this;
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public static BossLibrary getInstance() {
        return instance;
    }

}
