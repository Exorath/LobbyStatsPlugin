package com.exorath.plugin.lobbyStats;

import com.exorath.clickents.ClickEntAPI;
import com.exorath.plugin.lobbyStats.display.DisplayManager;
import com.exorath.service.stats.api.StatsServiceAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by toonsev on 6/5/2017.
 */
public class Main extends JavaPlugin {
    private static Main instance;
    @Override
    public void onEnable() {
        Main.instance = this;
        ClickEntAPI clickEntAPI = new ClickEntAPI(this);
        Bukkit.getPluginManager().registerEvents(new DisplayManager(
                new PositionProvider(new StatsServiceAPI(getStatsServiceAddress())), clickEntAPI), this);
    }


    private String getStatsServiceAddress(){
        String address = System.getenv("STATS_SERVICE_ADDRESS");
        if (address == null)
            Main.terminate("No STATS_SERVICE_ADDRESS env found.");
        return address;
    }
    public static void terminate() {
        System.out.println("1v1Plugin is terminating...");
        Bukkit.shutdown();
        System.out.println("Termination failed, force exiting system...");
        System.exit(1);
    }

    public static void terminate(String message) {
        System.out.println(message);
        System.out.println("1v1Plugin is terminating...");
        Bukkit.shutdown();
        System.out.println("Termination failed, force exiting system...");
        System.exit(1);
    }


    public static Main getInstance() {
        return instance;
    }
}
