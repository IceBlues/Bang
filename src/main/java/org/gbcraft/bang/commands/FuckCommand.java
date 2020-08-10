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
import org.gbcraft.bang.commands.bean.ContainerManager;
import org.gbcraft.bang.commands.bean.PlayerContainer;
import org.gbcraft.bang.config.OfflinePlayersConfig;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FuckCommand extends MFCommand {
    public FuckCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if (args.length >= 2) {
            OfflinePlayer player = OfflinePlayersConfig.get(args[1]);
            ContainerManager.prePut(this.getClass(), sender, player);
        }
        else {
            plugin.sendMessage(sender, "info.command.fuck");
        }


        return true;
    }

    public static void fuckInit(JavaPlugin plugin, PlayerContainer container) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            container.getIns().forEach((k, v) -> {
                String name = v.getName();
                if (null != name) {
                    Player player = Bukkit.getPlayer(name);
                    if (null != player) {
                        player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&c你看起来有点卡"), ChatColor.translateAlternateColorCodes('&', "&c不如问问万能的鸡神你做了什么"), 10, 70, 20);
                        fuck(protocolManager, player);
                    }
                }

            });
        }, 20, 20 * 7);
    }

    private static void fuck(ProtocolManager protocolManager, Player player) {
        Location location = player.getLocation().clone();
        PacketContainer fakeExplosion = protocolManager.
                createPacket(PacketType.Play.Server.EXPLOSION);

        fakeExplosion.getDoubles().
                write(0, player.getLocation().getX()).
                write(1, player.getLocation().getY()).
                write(2, player.getLocation().getZ());
        fakeExplosion.getFloat().
                write(0, 30F).
                write(1, 700F).
                write(2, 700F).
                write(3, 700F);

        List<BlockPosition> list = new ArrayList<>();
        list.add(new BlockPosition(1, 1, 1));
        fakeExplosion.getBlockPositionCollectionModifier().write(0, list);

        Bukkit.getScheduler().runTaskLater(Bang.getPlugin(Bang.class), () -> {
            player.teleport(location);
        }, 20);
        try {
            protocolManager.sendServerPacket(player, fakeExplosion);
        }
        catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Cannot send packet " + fakeExplosion, e);
        }
    }
}
