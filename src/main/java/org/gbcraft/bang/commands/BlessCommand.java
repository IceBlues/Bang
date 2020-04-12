package org.gbcraft.bang.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.config.OfflinePlayersConfig;

import java.util.HashMap;
import java.util.Map;

public class BlessCommand extends MFCommand {
    private final static Map<String, OfflinePlayer> containers = new HashMap<>();

    public BlessCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if (sender.hasPermission("bang.base") && args.length >= 2) {
            OfflinePlayer player = OfflinePlayersConfig.get(args[1]);

            if (null != player) {
                containers.put(player.getName(), player);
                sender.sendMessage("添加成功");
            }
        }
        return true;
    }

    public static boolean isContain(String name){
        return containers.get(name) != null;
    }

    public static void remove(String name){
        Player player = Bukkit.getPlayer(name);
        if(null != player){
            for(PotionEffect effect : player.getActivePotionEffects())
            {
                player.removePotionEffect(effect.getType());
            }
        }
        containers.remove(name);
    }

    public static String[] getKeyArray(){
        return containers.keySet().toArray(new String[0]);
    }

    public static String printContainers(){
        StringBuilder builder = new StringBuilder();
        containers.forEach((k, v) -> builder.append(k));
        return builder.toString();
    }
}
