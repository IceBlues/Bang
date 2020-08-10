package org.gbcraft.bang.commands.bean;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.gbcraft.bang.config.ConfigHelper;
import org.gbcraft.bang.config.OfflinePlayersConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerContainer {
    private final Map<String, OfflinePlayer> containers = new HashMap<>();

    private final JavaPlugin plugin;
    private final String root;

    public PlayerContainer(JavaPlugin plugin, CommandName name) {
        this.plugin = plugin;
        this.root = name.name().toLowerCase();

        List<String> players = ConfigHelper.getPlayersList(root, plugin);
        players.forEach(p -> {
            OfflinePlayer player = OfflinePlayersConfig.get(p);
            if (null != player) {
                containers.put(p, player);
            }
        });
    }

    public void put(String name, OfflinePlayer player) {
        containers.put(name, player);
    }

    public boolean isContain(String name) {
        return containers.get(name) != null;
    }

    public String remove(String name) {
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

    public void save() {
        List<String> list = new ArrayList<>();
        containers.forEach((k, v) -> {
            list.add(k);
        });
        ConfigHelper.savePlayersList(root, list, plugin);
    }

    public String[] getKeyArray() {
        return containers.keySet().toArray(new String[0]);
    }
    
    public Map<String, OfflinePlayer> getIns(){
        return containers;
    }

    public String printContainers() {
        StringBuilder builder = new StringBuilder();
        containers.forEach((k, v) -> builder.append(k).append(" "));
        return builder.toString();
    }
}
