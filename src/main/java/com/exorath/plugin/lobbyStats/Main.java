package com.exorath.plugin.lobbyStats;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by toonsev on 6/5/2017.
 */
public class Main extends JavaPlugin {
    @Override
    public void onEnable() {

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

}
