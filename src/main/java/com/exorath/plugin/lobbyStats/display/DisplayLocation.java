package com.exorath.plugin.lobbyStats.display;

import com.exorath.plugin.lobbyStats.res.Display;
import com.exorath.service.stats.api.StatsServiceAPI;
import org.bukkit.World;

/**
 * Created by toonsev on 6/8/2017.
 */
public class DisplayLocation {
    private StatsServiceAPI statsServiceAPI;
    private World world;
    private String gameId;
    private Display display;

    public DisplayLocation(StatsServiceAPI statsServiceAPI, World world, String gameId, Display display) {
        this.statsServiceAPI = statsServiceAPI;
        this.world = world;
        this.gameId = gameId;
        this.display = display;
    }


}
