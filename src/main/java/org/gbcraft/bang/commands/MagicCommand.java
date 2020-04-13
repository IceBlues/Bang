package org.gbcraft.bang.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.config.ConfigHelper;
import org.gbcraft.bang.config.OfflinePlayersConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MagicCommand extends MFCommand {
    private final static Map<String, OfflinePlayer> containers = new HashMap<>();
    public MagicCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    public static void init(JavaPlugin plugin){
        List<String> players = ConfigHelper.getPlayersList("magic", plugin);
        players.forEach(p -> {
            OfflinePlayer player = OfflinePlayersConfig.get(p);
            if(null != player){
                containers.put(p, player);
            }
        });
    }

    public static void save(JavaPlugin plugin){
        List<String> list = new ArrayList<>();
        containers.forEach((k,v)->{
            list.add(k);
        });
        ConfigHelper.savePlayersList("magic", list, plugin);
    }

    @Override
    public boolean run() {
        if (sender.hasPermission("bang.base")) {
            if(args.length >= 2) {
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
            else{
                plugin.sendMessage(sender, "info.command.magic");
            }
        }
        else{
            plugin.sendMessage(sender, "info.permission.no");
        }
        return true;
    }

    public static boolean isContain(String name){
        return containers.get(name) != null;
    }

    public static String remove(String name){
        String node;

        Object obj = containers.remove(name);
        if(null != obj){
            node = "info.remove.no-online";
            Player player = Bukkit.getPlayer(name);
            if(null != player){
                for(PotionEffect effect : player.getActivePotionEffects())
                {
                    player.removePotionEffect(effect.getType());
                }
                node = "info.remove.success";
            }
        }
        else{
            node = "info.remove.no-player";
        }

        return node;
    }

    public static String[] getKeyArray(){
        return containers.keySet().toArray(new String[0]);
    }

    public static String printContainers(){
        StringBuilder builder = new StringBuilder();
        containers.forEach((k, v) -> builder.append(k).append(" "));
        return builder.toString();
    }
}
