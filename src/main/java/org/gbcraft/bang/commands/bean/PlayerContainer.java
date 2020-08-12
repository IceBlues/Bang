package org.gbcraft.bang.commands.bean;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.gbcraft.bang.config.ConfigHelper;

import java.util.*;

public class PlayerContainer {
    private final Map<String, CommandPlayer> containers = new HashMap<>();

    private final JavaPlugin plugin;
    private final String root;

    public PlayerContainer(JavaPlugin plugin, CommandName name) {
        this.plugin = plugin;
        this.root = name.name().toLowerCase();

        List<String> players = ConfigHelper.getPlayersList(root, plugin);
        players.forEach(p -> {
            String[] split = p.split("\\|");
            String pName = split[0];
            String[] descMsg = null;
            if (split.length > 1) {
                descMsg = Arrays.copyOfRange(split, 1, split.length);
            }
            put(pName, descMsg);
        });
    }

    public void put(String name, String[] desc) {
        if (null == desc || desc.length == 0) {
            put(name);
        }
        else {
            containers.put(name, new CommandPlayer(name, desc));
        }

    }

    public void put(String name) {
        containers.put(name, new CommandPlayer(name));
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

    public void releaseAll() {
        List<String> releaseList = new ArrayList<>();
        containers.forEach((s, player) -> {
            if (Bukkit.getBanList(BanList.Type.NAME).isBanned(player.playerName)) {
                releaseList.add(s);
            }
        });
        releaseList.forEach(containers::remove);
    }

    public void save() {
        List<String> list = new ArrayList<>();
        containers.forEach((k, v) -> {
            StringBuilder str = new StringBuilder();
            str.append(v.playerName);
            v.description.forEach(d -> {
                str.append("|");
                str.append(d);
            });
            list.add(str.toString());
        });
        ConfigHelper.savePlayersList(root, list, plugin);
    }

    public CommandPlayer getCommandPlayer(String playerName) {
        return containers.get(playerName);
    }

    public String[] getKeyArray() {
        return containers.keySet().toArray(new String[0]);
    }

    public boolean isEmpty() {
        return containers.isEmpty();
    }

    public String printContainers() {
        StringBuilder builder = new StringBuilder();
        containers.forEach((k, v) -> builder.append(k).append(" "));
        return builder.toString();
    }
}
