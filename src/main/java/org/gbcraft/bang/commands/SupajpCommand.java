package org.gbcraft.bang.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.config.OfflinePlayersConfig;

import java.util.HashMap;
import java.util.Map;

public class SupajpCommand extends MFCommand{
    private final static Map<String, OfflinePlayer> containers = new HashMap<>();
    public SupajpCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if(sender.hasPermission("bang.base")) {
            OfflinePlayer player = OfflinePlayersConfig.getPlayerMap().get(args[1]);

            if(null != player) {
                containers.put(args[1], player);
                sender.sendMessage("添加成功");
            }
        }
        return true;
    }

    public static boolean isContain(String name){
        return containers.get(name) != null;
    }

    public static boolean remove(String name){
        Boolean res = false;
        Player p;
        if(null != containers.get(name) && null != (p=Bukkit.getPlayer(name))){
            p.setFlying(false);
            p.setWalkSpeed(0.1f);
            p.setFlySpeed(0.2f);
            containers.remove(name);
            res = true;
        }
        return res;

    }

    public static String printContainers(){
        StringBuilder builder = new StringBuilder();
        containers.forEach((k, v) -> builder.append(k));
        return builder.toString();
    }
}
