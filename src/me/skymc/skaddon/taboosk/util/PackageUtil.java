package me.skymc.skaddon.taboosk.util;

import me.skymc.skaddon.taboosk.TabooSK;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

/**
 * @Author sky
 * @Since 2018-08-06 20:52
 */
public class PackageUtil {

    public static List<Class> getClasses() {
        List<Class> classes = new ArrayList<>();
        URL url = getCaller().getProtectionDomain().getCodeSource().getLocation();
        try {
            File src;
            try {
                src = new File(url.toURI());
            } catch (URISyntaxException e) {
                src = new File(url.getPath());
            }
            new JarFile(src).stream().filter(entry -> entry.getName().endsWith(".class")).forEach(entry -> {
                String className = entry.getName().replace('/', '.').substring(0, entry.getName().length() - 6);
                try {
                    classes.add(Class.forName(className, false, TabooSK.class.getClassLoader()));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    private static Class getCaller() {
        try {
            return Class.forName(Thread.currentThread().getStackTrace()[3].getClassName(), false, TabooSK.class.getClassLoader());
        } catch (ClassNotFoundException ignored) {
        }
        return null;
    }
}