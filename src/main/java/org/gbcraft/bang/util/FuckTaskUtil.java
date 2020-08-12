package org.gbcraft.bang.util;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.gbcraft.bang.Bang;

import java.util.HashMap;
import java.util.Map;

public class FuckTaskUtil {
    private static final Map<String, BukkitRunnable> taskMap = new HashMap<>();

    public static void append(String name) {
        BukkitRunnable bukkitRunnable = new BukkitRunnable() {
            float offset = 700F;
            final float max = 7000F;

            @Override
            public void run() {
                if (StringUtils.isNotBlank(name)) {
                    Player player = Bukkit.getPlayer(name);
                    if (null != player) {
                        player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&c你看起来有点卡"), ChatColor.translateAlternateColorCodes('&', "&c不如问问万能的鸡神你做了什么"), 10, 70, 20);
                        ProtocolUtil.boom(player, offset);
                        if (offset < max) {
                            offset = offset + 100F;
                        }
                    }
                }
            }
        };

        bukkitRunnable.runTaskTimer(Bang.getPlugin(Bang.class), 20, 20 * 7);
        taskMap.put(name, bukkitRunnable);
    }

    public static void remove(String name) {
        BukkitRunnable bukkitRunnable = taskMap.get(name);
        if (null != bukkitRunnable) {
            bukkitRunnable.cancel();
            taskMap.remove(name);
        }
    }

}
