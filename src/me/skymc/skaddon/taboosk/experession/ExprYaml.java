package me.skymc.skaddon.taboosk.experession;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.skymc.skaddon.taboosk.TabooSK;
import me.skymc.skaddon.taboosk.yaml.CacheYaml;
import me.skymc.taboolib.playerdata.DataUtils;
import me.skymc.taboolib.string.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExprYaml extends SimpleExpression<Object> {

    private Expression<String> node;
    private Expression<String> file;
    private Expression<Object> def;
    private States state;

    private enum States {
        VALUE, VALUES, VALUES_ALL, NODES, NODES_ALL, LIST,
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public boolean isSingle() {
        return this.state == States.VALUE;
    }

    @Override
    public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parse) {
        if (parse.mark == 1) {
            this.state = States.VALUE;
        } else if (parse.mark == 2) {
            this.state = States.NODES;
        } else if (parse.mark == 3) {
            this.state = States.NODES_ALL;
        } else if (parse.mark == 4) {
            this.state = States.LIST;
        } else if (parse.mark == 5) {
            this.state = States.VALUES;
        } else if (parse.mark == 6) {
            this.state = States.VALUES_ALL;
        }
        this.node = (Expression<String>) e[0];
        this.file = (Expression<String>) e[1];
        this.def = e.length > 2 ? (Expression<Object>) e[2] : null;
        return true;
    }

    @Override
    public String toString(@Nullable Event e, boolean arg1) {
        return this.getClass().getName();
    }

    @Override
    @Nullable
    protected Object[] get(Event e) {
        String filePath = this.file.getSingle(e).replace("/'", File.separator);
        FileConfiguration conf;
        if (CacheYaml.CACHE_YAML.containsKey(filePath)) {
            CacheYaml yaml = CacheYaml.CACHE_YAML.get(filePath);
            conf = yaml.getConf();
            yaml.update();
        } else {
            File file = new File(filePath);
            try {
                createFileAndPath(file);
            } catch (IOException error) {
                error.printStackTrace();
            }
            conf = YamlConfiguration.loadConfiguration(file);
            CacheYaml.CACHE_YAML.put(filePath, new CacheYaml(file, conf, System.currentTimeMillis()));
        }
        if (!conf.contains(this.node.getSingle(e))) {
            return def != null ? CollectionUtils.array(def.getSingle(e)) : null;
        }
        switch (this.state) {
            case NODES:
                return conf.getConfigurationSection(this.node.getSingle(e)).getKeys(false).toArray();
            case NODES_ALL:
                return conf.getConfigurationSection(this.node.getSingle(e)).getKeys(true).toArray();
            case VALUES:
                return conf.getConfigurationSection(this.node.getSingle(e)).getValues(false).entrySet().toArray();
            case VALUES_ALL:
                return conf.getConfigurationSection(this.node.getSingle(e)).getValues(true).entrySet().toArray();
            case LIST:
                return conf.getList(this.node.getSingle(e)).toArray();
            default:
                return CollectionUtils.array(conf.get(this.node.getSingle(e)));
        }
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        String filePath = this.file.getSingle(e).replace("/'", File.separator);
        File file;
        FileConfiguration conf;
        if (CacheYaml.CACHE_YAML.containsKey(filePath)) {
            CacheYaml yaml = CacheYaml.CACHE_YAML.get(filePath);
            file = yaml.getFile();
            conf = yaml.getConf();
            yaml.update();
        } else {
            file = new File(filePath);
            try {
                createFileAndPath(file);
            } catch (IOException error) {
                error.printStackTrace();
            }
            conf = YamlConfiguration.loadConfiguration(file);
            CacheYaml.CACHE_YAML.put(filePath, new CacheYaml(file, conf, System.currentTimeMillis()));
        }
        if (mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET) {
            conf.set(this.node.getSingle(e), null);
        } else {
            switch (this.state) {
                case VALUE:
                    if (mode == Changer.ChangeMode.SET) {
                        conf.set(this.node.getSingle(e), delta[0]);
                    }
                    break;
                case NODES:
                case NODES_ALL:
                    if (mode == Changer.ChangeMode.ADD) {
                        conf.createSection(this.node.getSingle(e));
                    } else if (mode == Changer.ChangeMode.REMOVE) {
                        conf.set(this.node.getSingle(e) + "." + (delta[0] == null ? "null" : delta[0]), null);
                    }
                    break;
                case LIST:
                    if (mode == Changer.ChangeMode.SET) {
                        conf.set(this.node.getSingle(e), delta[0]);
                    } else if (mode == Changer.ChangeMode.ADD) {
                        List objects = conf.getList(this.node.getSingle(e), new ArrayList<>());
                        objects.addAll(ArrayUtils.asList(delta));
                        conf.set(this.node.getSingle(e), objects);
                    } else if (mode == Changer.ChangeMode.REMOVE) {
                        List objects = conf.getList(this.node.getSingle(e), new ArrayList<>());
                        objects.removeAll(ArrayUtils.asList(delta));
                        conf.set(this.node.getSingle(e), objects);
                    }
                    break;
                default:
            }
        }
        Optional.ofNullable(CacheYaml.CACHE_YAML_SAVING_TASK.put(filePath, new BukkitRunnable() {
            @Override
            public void run() {
                DataUtils.saveConfiguration(conf, file);
            }
        }.runTaskLaterAsynchronously(TabooSK.getInst(), 20))).ifPresent(BukkitTask::cancel);
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if ((mode == Changer.ChangeMode.DELETE) || (mode == Changer.ChangeMode.RESET)) {
            return (Class[]) CollectionUtils.array(new Class[] {Object.class});
        }
        if (this.state == States.VALUE) {
            if (mode == Changer.ChangeMode.SET) {
                return (Class[]) CollectionUtils.array(new Class[] {Object.class});
            }
        } else if ((this.state == States.LIST) && ((mode == Changer.ChangeMode.ADD) || (mode == Changer.ChangeMode.REMOVE))) {
            return (Class[]) CollectionUtils.array(new Class[] {Object.class});
        }
        return null;
    }

    public static void createFileAndPath(File file) throws IOException {
        if (!file.exists()) {
            String filePath = file.getPath();
            int index = filePath.lastIndexOf(File.separator);
            String folderPath;
            File folder;
            if ((index >= 0) && (!(folder = new File(filePath.substring(0, index))).exists())) {
                folder.mkdirs();
            }
            file.createNewFile();
        }
    }
}
