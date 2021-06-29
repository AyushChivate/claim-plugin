package io.ayushchivate.github.claimplugin;

import net.dohaw.corelib.CoreLib;
import net.dohaw.corelib.JPUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class ClaimPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        /* hook into the CoreLib library */
        System.out.println("Updated version");
        CoreLib.setInstance(this);
        JPUtils.registerCommand("claim", new ClaimCommand());
        JPUtils.registerCommand("unclaim", new UnclaimCommand());
        JPUtils.registerCommand("trust", new TrustCommand());
        JPUtils.registerCommand("untrust", new UntrustCommand());
        JPUtils.registerCommand("flags", new FlagsCommand(this));
        JPUtils.registerEvents(new FlagListener());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
