package org.gbcraft.bang.commands;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.config.ConfigHelper;
import org.gbcraft.bang.config.OfflinePlayersConfig;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuckCommand extends MFCommand {
    private static ProtocolManager protocolManager;
    private final static Map<String, OfflinePlayer> containers = new HashMap<>();

    public FuckCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    public static void init(JavaPlugin plugin) {
        protocolManager = ProtocolLibrary.getProtocolManager();
        List<String> players = ConfigHelper.getPlayersList("fuck", plugin);
        players.forEach(p -> {
            OfflinePlayer player = OfflinePlayersConfig.get(p);
            if (null != player) {
                containers.put(p, player);
            }
        });

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            containers.forEach((k, v) -> {
                if (v.isOnline()) {
                    Player player = v.getPlayer();
                    if (null != player) {
                        player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&c你妈死了"), ChatColor.translateAlternateColorCodes('&', "&c熊孩子死妈"), 10, 70, 20);
                        fuck(player);
                    }
                }
            });
        }, 20, 20*4);
    }

    public static void save(JavaPlugin plugin) {
        List<String> list = new ArrayList<>();
        containers.forEach((k, v) -> {
            list.add(k);
        });
        ConfigHelper.savePlayersList("fuck", list, plugin);
    }

    @Override
    public boolean run() {
        if (args.length >= 2) {
            OfflinePlayer player = OfflinePlayersConfig.get(args[1]);

            if (null != player) {
                if (!isContain(player.getName())) {
                    containers.put(player.getName(), player);

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
        else {
            plugin.sendMessage(sender, "info.command.fuck");
        }


        return true;
    }

    public static boolean isContain(String name) {
        return containers.get(name) != null;
    }

    public static String remove(String name) {
        String node;
        Object obj = containers.remove(name);
        if (null != obj) {
            node = "info.remove.success";
        }
        else {
            node = "info.remove.no-player";
        }
        return node;
    }

    public static String[] getKeyArray() {
        return containers.keySet().toArray(new String[0]);
    }

    public static String printContainers() {
        StringBuilder builder = new StringBuilder();
        containers.forEach((k, v) -> builder.append(k).append(" "));
        return builder.toString();
    }

    private static void fuck(Player player) {
        Location location = player.getLocation().clone();
        PacketContainer fakeExplosion = protocolManager.
                createPacket(PacketType.Play.Server.EXPLOSION);

        fakeExplosion.getDoubles().
                write(0, player.getLocation().getX()).
                write(1, player.getLocation().getY()).
                write(2, player.getLocation().getZ());
        fakeExplosion.getFloat().
                write(0, 0F).
                write(1, 7000F).
                write(2, 7000F).
                write(3, 7000F);

        List<BlockPosition> list = new ArrayList<>();
        list.add(new BlockPosition(80000, 80000, 80000));
        fakeExplosion.getBlockPositionCollectionModifier().write(0, list);

        try {
            protocolManager.sendServerPacket(player, fakeExplosion);
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Cannot send packet " + fakeExplosion, e);
        }
    }
}
