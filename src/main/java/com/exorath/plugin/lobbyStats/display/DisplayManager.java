package com.exorath.plugin.lobbyStats.display;

import com.exorath.plugin.lobbyStats.Main;
import com.exorath.plugin.lobbyStats.res.DisplayPackage;
import com.exorath.service.stats.api.StatsServiceAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by toonsev on 6/5/2017.
 */
public class DisplayManager implements Listener {
    private static final Yaml YAML = new Yaml();
    private StatsServiceAPI statsServiceAPI;

    private HashMap<World, DisplayWorld> displayWorlds = new HashMap<>();

    public DisplayManager(StatsServiceAPI statsServiceAPI) {
        Bukkit.getWorlds().forEach(world -> loadWorld(world));
        this.statsServiceAPI = statsServiceAPI;
    }

    @EventHandler(ignoreCancelled = true)
    public void onWorldLoad(WorldLoadEvent event) {
        loadWorld(event.getWorld());
    }

    private void loadWorld(World world) {
        File config = new File(world.getWorldFolder(), "lobbystats.yml");
        if (!config.exists() || !config.isFile())
            return;
        try (FileInputStream fis = new FileInputStream(config)) {
            DisplayPackage displayPackage = YAML.loadAs(fis, DisplayPackage.class);
            loadWorldWithDisplayPackage(world, displayPackage);
        } catch (IOException e) {
            e.printStackTrace();
            Main.terminate(e.getMessage());
        }
    }


    private void loadWorldWithDisplayPackage(World world, DisplayPackage displayPackage) {
        displayWorlds.put(world, new DisplayWorld(statsServiceAPI, world, displayPackage));
    }
}