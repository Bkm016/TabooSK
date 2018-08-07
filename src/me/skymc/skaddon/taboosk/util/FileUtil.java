package me.skymc.skaddon.taboosk.util;

import com.google.common.io.Files;
import com.ilummc.tlib.resources.TLocale;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.nio.charset.Charset;

/**
 * @Author sky
 * @Since 2018-08-07 16:40
 */
public class FileUtil {

    public static FileConfiguration saveDefaultConfig(Plugin plugin, String name) {
        File file = new File(plugin.getDataFolder(), name);
        if (!file.exists()) {
            plugin.saveResource(name, true);
        }
        return load(plugin, file);
    }

    public static FileConfiguration load(Plugin plugin, File file) {
        return loadYaml(plugin, file);
    }

    public static YamlConfiguration loadYaml(Plugin plugin, File file) {
        YamlConfiguration configuration = new YamlConfiguration();
        try {
            String yaml = Files.toString(file, Charset.forName("utf-8"));
            configuration.loadFromString(yaml);
            return configuration;
        } catch (Exception var4) {
            TLocale.Logger.error("FILE-UTILS.FAIL-LOAD-CONFIGURATION", plugin.getName(), file.getName());
            return configuration;
        }
    }
}
