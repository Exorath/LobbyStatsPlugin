package com.exorath.plugin.lobbyStats.display;

import com.exorath.clickents.ClickEntAPI;
import com.exorath.clickents.api.ClickableEntity;
import com.exorath.exoHUD.DisplayProperties;
import com.exorath.exoHUD.HUDText;
import com.exorath.exoHUD.locations.row.HologramLocation;
import com.exorath.exoHUD.removers.NeverRemover;
import com.exorath.exoHUD.texts.ChatColorText;
import com.exorath.exoHUD.texts.PlainText;
import com.exorath.plugin.lobbyStats.Main;
import com.exorath.plugin.lobbyStats.PositionProvider;
import com.exorath.plugin.lobbyStats.res.Display;
import com.exorath.service.stats.api.StatsServiceAPI;
import com.exorath.service.stats.res.GetTopPlayersReq;
import com.exorath.service.stats.res.GetTopPlayersRes;
import com.exorath.service.stats.res.TopPlayer;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.EulerAngle;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by toonsev on 6/8/2017.
 */
public class DisplayLocation {
    private static final long UPDATE_PERIOD = 10;
    private static final TimeUnit UPDATE_UNIT = TimeUnit.SECONDS;
    private PositionProvider positionProvider;
    private ClickEntAPI clickEntAPI;
    private World world;
    private String gameId;
    private Display display;

    private Updater updater;

    private HologramLocation hologramLocation;

    public DisplayLocation(PositionProvider positionProvider, ClickEntAPI clickEntAPI, World world, String gameId, Display display) {
        this.positionProvider = positionProvider;
        this.clickEntAPI = clickEntAPI;

        this.world = world;
        this.gameId = gameId;
        this.display = display;
        place();
        this.updater = new Updater();
        loadHologram(updater);

        scheduleUpdates();
    }

    private void loadHologram(Updater updater) {
        this.hologramLocation = new HologramLocation(getLocation().clone().add(0, 2.5d, 0));
        for (int i = 0; i < display.getLore().size(); i++) {
            String line = ChatColor.translateAlternateColorCodes('&', display.getLore().get(i));
            addText(hologramLocation, new PlainText(line), display.getLore().size() - i);
        }
        addText(hologramLocation, new ChatColorText(new PlainText(getPositionString())).color(getPositionColor()).bold(true), 0);

    }

    private void update(TopPlayer topPlayer) {
        //No update stuff other then the hologram yet.
    }

    private synchronized Location getLocation() {
        return new Location(world, display.getLocation().getX(), display.getLocation().getY(), display.getLocation().getZ(), display.getLocation().getYaw(), display.getLocation().getPitch());
    }

    private void scheduleUpdates() {
        Schedulers.io().schedulePeriodicallyDirect(() -> {
            TopPlayer player = getPlayer();
            update(player);
            updater.update(player);
        }, 0, UPDATE_PERIOD, UPDATE_UNIT);
    }

    private void place() {
        ArmorStand armorStand = world.spawn(getLocation(), ArmorStand.class, armorStand1 -> armorStand1.setMetadata("doNotDespawn", new FixedMetadataValue(Main.getInstance(), "")));
        armorStand.setLeftArmPose(new EulerAngle(0d, 0d, 3.83972d));
        armorStand.setArms(true);
        armorStand.setBasePlate(true);
        switch (display.getPosition()) {
            case 1:
                armorStand.setItemInHand(new ItemStack(Material.GOLD_INGOT));
                break;
            case 2:
                armorStand.setItemInHand(new ItemStack(Material.IRON_INGOT));
                break;
            case 3:
                armorStand.setItemInHand(new ItemStack(Material.CLAY_BRICK));
                break;
            default:
                break;
        }
        ClickableEntity clickableEntity = clickEntAPI.makeClickable(armorStand);
        Observable<PlayerInteractAtEntityEvent> observable = clickableEntity.getInteractObservable();
        observable.subscribe(event -> onArmorStandClick(event));
    }

    private synchronized TopPlayer getPlayer() {
        int amount = display.getPosition() <= 3 ? 3 : display.getPosition();
        GetTopPlayersRes res = positionProvider.getTopPlayers(new GetTopPlayersReq(gameId, display.getStat(), amount));
        if (res.getTopPlayers().size() < display.getPosition())
            return null;
        return res.getTopPlayers().get(display.getPosition() - 1);
    }


    private synchronized String getPositionString() {
        if (display.getPosition() == 1)
            return "1st";
        else if (display.getPosition() == 2)
            return "2nd";
        else if (display.getPosition() == 3)
            return "3rd";
        else
            return display.getPosition() + "th";

    }

    private synchronized ChatColor getPositionColor() {
        switch (display.getPosition()) {
            case 1:
                return ChatColor.YELLOW;
            case 2:
                return ChatColor.GRAY;
            case 3:
                return ChatColor.GOLD;
            default:
                return ChatColor.DARK_GRAY;
        }
    }

    private void onArmorStandClick(PlayerInteractAtEntityEvent event) {
        event.getPlayer().sendMessage("Clicked " + display.getStat() + " position: " + display.getPosition());
    }

    private class Updater implements HUDText {
        private PublishSubject<List<TextComponent>> subject = PublishSubject.create();

        public void update(TopPlayer topPlayer) {
            if (topPlayer == null) {
                TextComponent noPlayerComp = new TextComponent("No " + getPositionString() + " Player");
                noPlayerComp.setColor(ChatColor.RED);
                subject.onNext(Arrays.asList(noPlayerComp));
            } else {
                TextComponent component = new TextComponent(topPlayer.getName() + " - " + topPlayer.getAmount());
                component.setColor(getPositionColor());
                subject.onNext(Arrays.asList(component));
            }
        }

        @Override
        public Observable<List<TextComponent>> getTextObservable() {
            return subject;
        }
    }


    private void addText(HologramLocation hologramLocation, HUDText text, double priority) {
        hologramLocation.addText(
                text, DisplayProperties.create(priority, NeverRemover.never()));
        hologramLocation.teleport(hologramLocation.getLocation().clone().add(0, 0.25d, 0));
    }
}
