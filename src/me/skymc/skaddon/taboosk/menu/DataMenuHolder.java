package me.skymc.skaddon.taboosk.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;

/**
 * @Author sky
 * @Since 2018-08-06 16:09
 */
public class DataMenuHolder implements InventoryHolder {

    private HashMap<String, Object> menuData = new HashMap<>();

    public HashMap<String, Object> getMenuData() {
        return menuData;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }
}
