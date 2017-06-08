package com.exorath.plugin.lobbyStats.res;

import java.util.List;

/**
 * Created by toonsev on 6/8/2017.
 */
public class Display {
    private String stat;
    private List<String> lore;
    private int position;
    private YamlVector Location;

    public String getStat() {
        return stat;
    }

    public List<String> getLore() {
        return lore;
    }

    public int getPosition() {
        return position;
    }

    public YamlVector getLocation() {
        return Location;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setLocation(YamlVector location) {
        Location = location;
    }
}
