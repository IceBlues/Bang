package org.gbcraft.bang.listener;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ListenerManager {
    public static void initAll(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new EntityDeathListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), plugin);
    }
}
