package org.gbcraft.bang.commands.bean;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.commands.MFCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ContainerManager {
    private static Map<Class<? extends MFCommand>, PlayerContainer> map = new HashMap<>();

    public static void init(JavaPlugin plugin) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (CommandName value : CommandName.values()) {
            map.put(value.CommandClass(), new PlayerContainer(plugin, value));
            if (value == CommandName.FUCK) {
                CommandName.FUCK.CommandClass().getMethod("fuckInit", JavaPlugin.class, PlayerContainer.class).invoke(null, plugin, map.get(value.CommandClass()));
            }
        }
    }

    public static boolean isContain(CommandName name, String player) {
        return map.get(name.CommandClass()).isContain(player);
    }

    public static void prePut(Class<? extends MFCommand> clazz, CommandSender sender, OfflinePlayer player) {
        Bang plugin = Bang.getPlugin(Bang.class);
        PlayerContainer container = map.get(clazz);
        if (null != player) {
            if (!container.isContain(player.getName())) {
                container.put(player.getName(), player);
                plugin.sendMessage(sender, "info.add.success");
            }
            else {
                plugin.sendMessage(sender, "info.add.contained");
            }
        }
        else {
            plugin.sendMessage(sender, "info.player.no-player");
        }
    }

    // 先传枚举再转类是为了解开调用类和命令类的耦合
    public static String[] getArray(CommandName name) {
        return map.get(name.CommandClass()).getKeyArray();
    }

    public static String remove(CommandName className, String name) {
        Class<MFCommand> clazz = className.CommandClass();
        return map.get(clazz).remove(name);
    }

    public static void removeAll(String name) {
        for (CommandName value : CommandName.values()) {
            remove(value, name);
        }
    }

    public static String printContainers(CommandName name) {
        return map.get(name.CommandClass()).printContainers();
    }

    public static void saveAll() {
        map.forEach((k, v) -> {
            v.save();
        });
    }

}
