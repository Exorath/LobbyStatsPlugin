package com.exorath.plugin.lobbyStats.display;

import com.exorath.plugin.lobbyStats.res.Display;
import com.exorath.plugin.lobbyStats.res.DisplayPackage;
import com.exorath.service.stats.api.StatsServiceAPI;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by toonsev on 6/8/2017.
 */
public class DisplayWorld {
    private StatsServiceAPI statsServiceAPI;
    private World world;
    private Set<DisplayLocation> displayLocations = new HashSet<>();
    private DisplayPackage displayPackage;

    public DisplayWorld(StatsServiceAPI statsServiceAPI, World world, DisplayPackage displayPackage) {
        this.statsServiceAPI = statsServiceAPI;
        this.world = world;
        this.displayPackage = displayPackage;

        displayPackage.getDisplays().values().forEach(
                display -> addDisplayLocation(display));
    }

    private void addDisplayLocation(Display display) {
        displayLocations.add(new DisplayLocation(statsServiceAPI, world, displayPackage.getGameId(), display));
    }

    public DisplayPackage getDisplayPackage() {
        return displayPackage;
    }
}
