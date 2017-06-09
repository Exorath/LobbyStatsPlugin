package com.exorath.plugin.lobbyStats.display;

import com.exorath.clickents.ClickEntAPI;
import com.exorath.plugin.lobbyStats.PositionProvider;
import com.exorath.plugin.lobbyStats.res.Display;
import com.exorath.plugin.lobbyStats.res.DisplayPackage;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by toonsev on 6/8/2017.
 */
public class DisplayWorld {
    private PositionProvider positionProvider;
    private ClickEntAPI clickEntAPI;
    private World world;
    private Set<DisplayLocation> displayLocations = new HashSet<>();
    private DisplayPackage displayPackage;

    public DisplayWorld(PositionProvider positionProvider, ClickEntAPI clickEntAPI, World world, DisplayPackage displayPackage) {
        this.positionProvider = positionProvider;
        this.clickEntAPI = clickEntAPI;
        this.world = world;
        this.displayPackage = displayPackage;

        displayPackage.getDisplays().values().forEach(
                display -> addDisplayLocation(display));
    }

    private void addDisplayLocation(Display display) {
        displayLocations.add(new DisplayLocation(positionProvider, clickEntAPI, world, displayPackage.getGameId(), display));
    }

    public DisplayPackage getDisplayPackage() {
        return displayPackage;
    }
}