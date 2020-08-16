package org.gbcraft.bang.commands.bean;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.commands.MFCommand;
import org.gbcraft.bang.util.FuckTaskUtil;

import java.util.HashMap;
import java.util.Map;

public class ContainerManager {
    private final static Map<Class<? extends MFCommand>, PlayerContainer> map = new HashMap<>();

    public static void init(JavaPlugin plugin) {
        for (CommandName value : CommandName.values()) {
            map.put(value.CommandClass(), new PlayerContainer(plugin, value));
        }
    }

    public static boolean isContain(CommandName name, String player) {
        return map.get(name.CommandClass()).isContain(player);
    }

    public static void prePut(Class<? extends MFCommand> clazz, CommandSender sender, OfflinePlayer player, String... desc) {
        Bang plugin = Bang.getPlugin(Bang.class);

        if (null != player) {
            PlayerContainer container = map.get(clazz);

            if (!container.isContain(player.getName())) {
                container.put(player.getName(), desc);
                if (clazz == CommandName.FUCK.CommandClass()) {
                    FuckTaskUtil.append(player.getName());
                }
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

    public static void appendDesc(CommandName name, String playerName, String desc) {
        CommandPlayer player = getCommandPlayer(name, playerName);
        if (null != player) {
            player.appendDesc(desc);
        }
    }

    public static CommandPlayer getCommandPlayer(CommandName name, String playerName) {
        return map.get(name.CommandClass()).getCommandPlayer(playerName);
    }

    // 先传枚举再转类是为了解开调用类和命令类的耦合
    public static String[] getArray(CommandName name) {
        PlayerContainer container = map.get(name.CommandClass());
        return container != null ? container.getKeyArray() : new String[]{};
    }

    public static boolean isEmpty(CommandName name) {
        return !map.containsKey(name.CommandClass()) || map.get(name.CommandClass()).isEmpty();
    }

    public static String remove(CommandName className, String name) {
        Class<MFCommand> clazz = className.CommandClass();
        PlayerContainer container = map.get(clazz);
        String node = "info.remove.no-player";
        if (null != container) {
            if (clazz == CommandName.FUCK.CommandClass()) {
                FuckTaskUtil.remove(name);
            }
            node = container.remove(name);
        }

        return node;
    }

    public static void removeAll(String name) {
        for (CommandName value : CommandName.values()) {
            remove(value, name);
        }
    }

    public static void releaseAll() {
        map.forEach((aClass, container) -> {
            container.releaseAll();
        });
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
