package org.gbcraft.bang.config;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigHelper {
    public static List<String> getPlayersList(String key, JavaPlugin plugin){
        List<String> res = null;
        File file = new File(plugin.getDataFolder(), "players.yml");
        Configuration config = YamlConfiguration.loadConfiguration(file);
        res = config.getStringList(key);

        return res;
    }

    public static void savePlayersList(String key, List<String> list, JavaPlugin plugin){
        File file = new File(plugin.getDataFolder(), "players.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set(key, list);
        try {
            config.save(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
