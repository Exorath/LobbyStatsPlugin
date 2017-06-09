package com.exorath.plugin.lobbyStats.res;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by toonsev on 6/8/2017.
 */
public class DisplayPackage {
    private String gameId;
    private Map<String, Display> displays;

    public DisplayPackage() {}

    public String getGameId() {
        return gameId;
    }

    public Map<String, Display> getDisplays() {
        return displays;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public void setDisplays(HashMap<String, Display> displays) {
        this.displays = displays;
    }
}
