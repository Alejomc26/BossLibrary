package io.github.alejomc26;

import io.github.alejomc26.listener.BossLibraryListener;
import org.bukkit.plugin.java.JavaPlugin;

public class BossLibrary extends JavaPlugin {

    private static BossLibrary instance;
    @Override
    public void onEnable() {
        instance = this;
        this.getServer().getPluginManager().registerEvents(new BossLibraryListener(), this);
    }

    public static BossLibrary getInstance() {
        return instance;
    }

}
