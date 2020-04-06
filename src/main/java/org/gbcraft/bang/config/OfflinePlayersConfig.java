package org.gbcraft.bang.config;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

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
            if(null != name && !name.equals("")) {
                playerMap.put(name.toLowerCase(), offlinePlayer);
            }
        }
    }

    public static Map<String, OfflinePlayer> getPlayerMap() {
        return config.playerMap;
    }
}
