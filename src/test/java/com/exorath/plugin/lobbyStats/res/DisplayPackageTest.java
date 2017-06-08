package com.exorath.plugin.lobbyStats.res;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import static org.junit.Assert.assertEquals;

/**
 * Created by toonsev on 6/8/2017.
 */
public class DisplayPackageTest {
    @Test
    public void testYamlParsing(){
        Yaml yaml = new Yaml();
        DisplayPackage displayPackage = yaml.loadAs("gameId: cakewars\n" +
                "displays:\n" +
                "  display1:\n" +
                "    stat: wins\n" +
                "    lore:\n" +
                "    - Top Weekly Wins\n" +
                "    - \"(1000 crumbs)\"\n" +
                "    position: 1\n" +
                "    location:\n" +
                "      x: 0\n" +
                "      y: 0\n" +
                "      z: 0\n" +
                "  display2:\n" +
                "    stat: wins\n" +
                "    position: 2\n" +
                "    lore:\n" +
                "    - \"(500 crumbs)\"\n" +
                "    location:\n" +
                "      x: 5\n" +
                "      y: 12.5\n" +
                "      z: 7\n", DisplayPackage.class);
        assertEquals(displayPackage.getGameId(), "cakewars");
        assertEquals(displayPackage.getDisplays().size(), 2);
        assertEquals(displayPackage.getDisplays().get("display2").getStat(), "wins");
        assertEquals(displayPackage.getDisplays().get("display2").getPosition(), 2);
        assertEquals(displayPackage.getDisplays().get("display2").getLore().size(), 1);
        assertEquals(displayPackage.getDisplays().get("display2").getLore().get(0), "(500 crumbs)");
        assertEquals(displayPackage.getDisplays().get("display2").getLocation().getX(), 5, 0.1);
        assertEquals(displayPackage.getDisplays().get("display2").getLocation().getY(), 12.5, 0.1);
        assertEquals(displayPackage.getDisplays().get("display2").getLocation().getZ(), 7, 0.1);
    }
}
