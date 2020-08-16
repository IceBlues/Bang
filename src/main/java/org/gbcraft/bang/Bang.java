package org.gbcraft.bang;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.gbcraft.bang.commands.MFCommandExecutor;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.ContainerManager;
import org.gbcraft.bang.exception.PluginNotFoundException;
import org.gbcraft.bang.listener.ListenerManager;
import org.gbcraft.bang.util.FuckTaskUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public final class Bang extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveResource("players.yml", false);

        try {
            ContainerManager.init(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ListenerManager.initAll(this);

        initCycleTask();
        PluginCommand bang = Bukkit.getPluginCommand("bang");
        if (null != bang) {
            MFCommandExecutor executor = new MFCommandExecutor(this);
            bang.setExecutor(executor);
            bang.setTabCompleter(executor);
        }
        else {
            try {
                throw new PluginNotFoundException();
            }
            catch (PluginNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void initCycleTask() {
        String[] fuckPlayers = ContainerManager.getArray(CommandName.FUCK);
        for (String name : fuckPlayers) {
            FuckTaskUtil.append(name);
        }

        // dead
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            if (!ContainerManager.isEmpty(CommandName.DEAD)) {
                String[] players = ContainerManager.getArray(CommandName.DEAD);
                for (String p : players) {
                    Player player = Bukkit.getPlayer(p);
                    if (null != player) {
                        player.addScoreboardTag("Bang");
                        player.setHealth(0);
                    }
                }
            }
        }, 20 * 10, 20);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, ContainerManager::releaseAll, 1, 20 * 60 * 30);
    }

    @Override
    public void onDisable() {
        ContainerManager.saveAll();
    }

    public void sendMessage(CommandSender sender, String node) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(getConfig().getString(node))));
    }

    public void logToFile(String msg) {
        File dataFolder = getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }
        File saveTo = new File(getDataFolder(), "log/log.txt");
        try {
            if(!saveTo.getParentFile().exists()){
                saveTo.getParentFile().mkdirs();
            }
            if (!saveTo.exists()) {
                saveTo.createNewFile();
            }
            FileWriter fw = new FileWriter(saveTo, true);
            PrintWriter pw = new PrintWriter(fw);
            msg = new SimpleDateFormat("[HH:mm:ss]: ").format(new Date()) + msg;
            pw.println(msg);
            pw.flush();
            pw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveLog() {
        File log = new File(getDataFolder(), "log/log.txt");
        File saveLog = new File(getDataFolder(), "log/log-"+new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + ".txt");
        if(log.exists()){
            log.renameTo(saveLog);
        }
    }
}
