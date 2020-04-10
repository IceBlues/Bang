package org.gbcraft.bang.config;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class OfflinePlayersConfig {
    private static OfflinePlayersConfig config = new OfflinePlayersConfig();

    private Map<String, OfflinePlayer> playerMap;

    private OfflinePlayersConfig(){
        playerMap = new HashMap<>();
        OfflinePlayer[] offlinePlayers = Bukkit.getOfflinePlayers();
        for (OfflinePlayer offlinePlayer : offlinePlayers) {
            String name = offlinePlayer.getName();
            if(null != name) {
                playerMap.put(name.toLowerCase(), offlinePlayer);
            }
        }
    }

    public static void append(String name, Player player){
        String tempName = name.toLowerCase();
        if(null == config.playerMap.get(tempName)){
            config.playerMap.put(name, player);
        }
    }

    public static boolean isContain(String name){
        return config.playerMap.get(name.toLowerCase())!=null;
    }

    public static Map<String, OfflinePlayer> getPlayerMap() {
        return config.playerMap;
    }
}
