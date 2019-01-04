package me.skymc.skaddon.taboosk.command;

import me.skymc.skaddon.taboosk.TabooSK;
import me.skymc.skaddon.taboosk.experession.ExprVariable;
import me.skymc.skaddon.taboosk.handler.ScriptHandler;
import me.skymc.skaddon.taboosk.handler.YamlHandler;
import me.skymc.taboolib.skript.SkriptHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @Author sky
 * @Since 2018-08-06 21:39
 */
public class MainCommand implements CommandExecutor, Listener {

    public MainCommand() {
        Bukkit.getPluginManager().registerEvents(this, TabooSK.getInst());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1) {
            ScriptHandler.reloadGlobalScripts();
            sender.sendMessage("§8[§3§lTabooSK§8] §7Reload successfully.");
            return true;
        }
        if (!(sender instanceof Player)) {
            return false;
        }
        Inventory inventory = Bukkit.createInventory(null, 27, "TabooSK Information.");
        ItemStack barr1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
        {
            ItemMeta itemMeta = barr1.getItemMeta();
            itemMeta.setDisplayName("§f");
            barr1.setItemMeta(itemMeta);
        }
        ItemStack barr2 = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        {
            ItemMeta itemMeta = barr2.getItemMeta();
            itemMeta.setDisplayName("§f");
            barr2.setItemMeta(itemMeta);
        }
        IntStream.range(0, 27).forEach(i -> inventory.setItem(i, barr1));
        inventory.setItem(10, barr2);
        inventory.setItem(11, barr2);
        inventory.setItem(12, barr2);
        inventory.setItem(14, barr2);
        inventory.setItem(15, barr2);
        inventory.setItem(16, barr2);
        ItemStack info = new ItemStack(Material.COMMAND, 1); {
            ItemMeta itemMeta = info.getItemMeta();
            itemMeta.setDisplayName("§f§nTabooSK Information");
            itemMeta.setLore(Arrays.asList(
                    "",
                    "§7 Author: §f@坏黑",
                    "",
                    "§7 Variable: §f" + ExprVariable.VARIABLES.size(),
                    "§7 Configuration: §f" + YamlHandler.CACHE_YAML.size(),
                    "§7 CompileScript: §f" + ScriptHandler.getScripts().size(),
                    "§7 CompileScript: §f" + ScriptHandler.getScriptsDynamic().size() + " §c(Dynamic)"
            ));
            info.setItemMeta(itemMeta);
        }
        inventory.setItem(13, info);
        ((Player) sender).openInventory(inventory);
        return true;
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equalsIgnoreCase("TabooSK Information.")) {
            e.setCancelled(true);
        }
    }
}
