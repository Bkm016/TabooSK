package me.skymc.skaddon.taboosk.event.effect;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.skymc.skaddon.taboosk.experession.ExprYaml;
import me.skymc.skaddon.taboosk.yaml.CacheYaml;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;

/**
 * @Author sky
 * @Since 2018-07-18 21:06
 */
public class EffectYamlReload extends Effect {

    private Expression<String> path;

    @Override
    protected void execute(Event event) {
        String filePath = path.getSingle(event).replace("/'", File.separator);
        File file = new File(filePath);
        try {
            ExprYaml.createFileAndPath(file);
        } catch (IOException error) {
            error.printStackTrace();
        }
        FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
        CacheYaml.CACHE_YAML.put(filePath, new CacheYaml(file, conf, System.currentTimeMillis()));
    }

    @Override
    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        path = (Expression<String>) expressions[0];
        return true;
    }
}
