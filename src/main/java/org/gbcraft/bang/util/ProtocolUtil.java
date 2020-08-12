package org.gbcraft.bang.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.gbcraft.bang.Bang;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ProtocolUtil {
    public static void boom(Player player, float offset) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        Location location = player.getLocation().clone();
        PacketContainer fakeExplosion = protocolManager.
                createPacket(PacketType.Play.Server.EXPLOSION);

        fakeExplosion.getDoubles().
                write(0, player.getLocation().getX()).
                write(1, player.getLocation().getY()).
                write(2, player.getLocation().getZ());
        fakeExplosion.getFloat().
                write(0, 30F).
                write(1, offset).
                write(2, offset).
                write(3, offset);

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
