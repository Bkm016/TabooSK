package me.skymc.skaddon.taboosk.handler;

import me.skymc.skaddon.taboosk.TabooSK;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author sky
 * @Since 2018-07-18 20:48
 */
public class YamlHandler {

    public static final ConcurrentHashMap<String, YamlHandler> CACHE_YAML = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, BukkitTask> CACHE_YAML_SAVING_TASK = new ConcurrentHashMap<>();

    public static void init() {
        new BukkitRunnable() {
            @Override
            public void run() {
                CACHE_YAML.entrySet().stream().filter(cacheYamlEntry -> System.currentTimeMillis() - cacheYamlEntry.getValue().getLastUpdate() >= 60 * 1000L).map(Map.Entry::getKey).forEach(CACHE_YAML::remove);
            }
        }.runTaskTimerAsynchronously(TabooSK.getInst(), 0, 20);
    }

    private File file;
    private FileConfiguration conf;
    private long lastUpdate;

    public YamlHandler(File file, FileConfiguration conf, long lastUpdate) {
        this.file = file;
        this.conf = conf;
        this.lastUpdate = lastUpdate;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConf() {
        return conf;
    }

    public void setConf(FileConfiguration conf) {
        this.conf = conf;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void update() {
        this.lastUpdate = System.currentTimeMillis();
    }
}
