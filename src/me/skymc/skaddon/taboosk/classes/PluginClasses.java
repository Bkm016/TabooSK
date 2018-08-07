package me.skymc.skaddon.taboosk.classes;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.log.ErrorQuality;
import ch.njol.skript.registrations.Classes;

/**
 * @Author sky
 * @Since 2018-08-06 21:22
 */
public class PluginClasses {

    public static void init() {
        Classes.registerClass(new ClassInfo(Effect.class, "teffect").parser(new Parser() {
            @Override
            public Effect parse(String s, ParseContext parseContext) {
                if ((s.length() > 2) && (s.charAt(0) == '[') && (s.charAt(s.length() - 1) == ']')) {
                    Effect e = Effect.parse(s.substring(1, s.length() - 1), null);
                    if (e == null) {
                        Skript.error(s + " is an invalid effect.", ErrorQuality.SEMANTIC_ERROR);
                    } else {
                        return e;
                    }
                }
                return null;
            }

            @Override
            public boolean canParse(ParseContext context) {
                return true;
            }

            @Override
            public String toString(Object o, int i) {
                return null;
            }

            @Override
            public String toVariableNameString(Object o) {
                return null;
            }

            @Override
            public String getVariableNamePattern() {
                return ".+";
            }
        }));
        Classes.registerClass(new ClassInfo(Condition.class, "tcondition").parser(new Parser() {
            @Override
            public Condition parse(String s, ParseContext parseContext) {
                if ((s.length() > 2) && (s.charAt(0) == '[') && (s.charAt(s.length() - 1) == ']')) {
                    Condition e = Condition.parse(s.substring(1, s.length() - 1), null);
                    if (e == null) {
                        Skript.error(s + " is an invalid condition.", ErrorQuality.SEMANTIC_ERROR);
                    } else {
                        return e;
                    }
                }
                return null;
            }

            @Override
            public boolean canParse(ParseContext context) {
                return true;
            }

            @Override
            public String toString(Object o, int i) {
                return null;
            }

            @Override
            public String toVariableNameString(Object o) {
                return null;
            }

            @Override
            public String getVariableNamePattern() {
                return ".+";
            }
        }));
    }
}
