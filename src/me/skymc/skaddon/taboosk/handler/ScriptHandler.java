package me.skymc.skaddon.taboosk.handler;

import jdk.nashorn.api.scripting.NashornScriptEngineFactory;
import me.skymc.skaddon.taboosk.TabooSK;
import me.skymc.skaddon.taboosk.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import javax.script.*;
import java.util.HashMap;
import java.util.Objects;

/**
 * @Author sky
 * @Since 2018-06-02 22:48
 */
public class ScriptHandler {

    private static ScriptEngine scriptEngine;
    private static ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    private static HashMap<String, CompiledScript> scripts = new HashMap<>();
    private static HashMap<String, CompiledScript> scriptsDynamic = new HashMap<>();
    private static FileConfiguration scriptsFile;

    public static void inst() {
        try {
            NashornScriptEngineFactory factory = (NashornScriptEngineFactory) scriptEngineManager.getEngineFactories().stream().filter(factories -> "Oracle Nashorn".equalsIgnoreCase(factories.getEngineName())).findFirst().orElse(null);
            scriptEngine = Objects.requireNonNull(factory).getScriptEngine("-doe", "--global-per-engine");
        } catch (Exception ignored) {
            scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        }
    }

    public static CompiledScript compile(String script) {
        try {
            Compilable compilable = (Compilable) scriptEngine;
            return compilable.compile(script);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§8[§3§lTabooSK§8] §4JavaScript §c" + script + "§4 Compile Failed: §c" + e.toString());
            return null;
        }
    }

    public static void reloadGlobalScripts() {
        scriptsFile = FileUtil.saveDefaultConfig(TabooSK.getInst(), "scripts.yml");
        scriptsFile.getConfigurationSection("").getKeys(false).forEach(name -> scripts.put(name, compile(scriptsFile.getString(name))));
    }

    // *********************************
    //
    //        Getter and Setter
    //
    // *********************************

    public static ScriptEngine getScriptEngine() {
        return scriptEngine;
    }

    public static ScriptEngineManager getScriptEngineManager() {
        return scriptEngineManager;
    }

    public static HashMap<String, CompiledScript> getScripts() {
        return scripts;
    }

    public static HashMap<String, CompiledScript> getScriptsDynamic() {
        return scriptsDynamic;
    }
}
