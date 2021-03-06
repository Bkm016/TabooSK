package me.skymc.skaddon.taboosk.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class ListenerLatestDoing implements Listener {

    public static HashMap<Player, String> clickType = new HashMap<>();
    public static HashMap<Player, String> fishState = new HashMap<>();

    @EventHandler (priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent e) {
        clickType.put((Player) e.getWhoClicked(), e.getClick().toString());
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onFish(PlayerFishEvent e) {
        fishState.put(e.getPlayer(), e.getState().name());
    }

    @EventHandler
    public void quit(final PlayerQuitEvent e) {
        clickType.remove(e.getPlayer());
        fishState.remove(e.getPlayer());
    }
}
