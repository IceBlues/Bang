package org.gbcraft.bang.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.config.OfflinePlayersConfig;

import java.util.HashMap;
import java.util.Map;

public class FuckCommand extends MFCommand {
    private final static Map<String, OfflinePlayer> containers = new HashMap<>();

    public FuckCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        if(args.length >= 2) {
            args[1] = args[1].toLowerCase();
        }
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

    public static void remove(String name){
        containers.remove(name);
    }

    public static String printContainers(){
        StringBuilder builder = new StringBuilder();
        containers.forEach((k, v) -> builder.append(k));
        return builder.toString();
    }
}
