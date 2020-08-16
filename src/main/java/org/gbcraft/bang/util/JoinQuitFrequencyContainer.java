package org.gbcraft.bang.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.commands.bean.BanChatType;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.ContainerManager;

import java.util.HashMap;
import java.util.Map;

public class JoinQuitFrequencyContainer {
    private final static Map<Player, Integer> container = new HashMap<>();

    public static void append(Player player) {
        //1. 有则自增 无则入0自增
        //2. 定时任务, 到后删除(1s 2t)
        Bang plugin = Bang.getPlugin(Bang.class);
        if (!container.containsKey(player)) {
            container.put(player, 0);
            Bukkit.getScheduler().runTaskLaterAsynchronously(Bang.getPlugin(Bang.class), () -> {
                container.remove(player);
            }, 20 * plugin.getConfig().getInt("logCycle"));
        }
        Integer integer = container.get(player);
        container.put(player, ++integer);
        if (integer >= plugin.getConfig().getInt("logTotal")) {
            plugin.logToFile("自动禁言 [LOGS] " + player.getName());
            if (ContainerManager.isContain(CommandName.BANCHAT, player.getName())) {
                ContainerManager.appendDesc(CommandName.BANCHAT, player.getName(), BanChatType.LOGS.type());
            }
            else {
                ContainerManager.prePut(CommandName.BANCHAT.CommandClass(), Bukkit.getConsoleSender(), player, BanChatType.LOGS.type());
            }

            Bukkit.getScheduler().runTaskLaterAsynchronously(Bang.getPlugin(Bang.class), () -> {
                ContainerManager.remove(CommandName.BANCHAT, player.getName());
            }, 20 * 60 * 30);
        }

    }
}
