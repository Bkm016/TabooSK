package me.skymc.skaddon.taboosk.util;

import ch.njol.skript.ScriptLoader;
import org.bukkit.event.Event;

/**
 * @Author 坏黑
 * @Since 2019-03-05 15:12
 */
public class Util {

    private static String currentEventName;
    private static Class<? extends Event>[] currentEvents;

    public static void toggleCurrentEvent(Class<? extends Event> event) {
        if (event != null) {
            currentEventName = ScriptLoader.getCurrentEventName();
            currentEvents = ScriptLoader.getCurrentEvents();
            ScriptLoader.setCurrentEvent(event.getSimpleName(), event);
        } else {
            ScriptLoader.setCurrentEvent(currentEventName, currentEvents);
        }
    }
}
