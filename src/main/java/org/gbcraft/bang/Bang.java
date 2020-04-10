package org.gbcraft.bang;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.gbcraft.bang.commands.*;
import org.gbcraft.bang.config.OfflinePlayersConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class Bang extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(this, this);
        MFCommandExecutor executor = new MFCommandExecutor(this);
        Bukkit.getPluginCommand("bang").setExecutor(executor);
        Bukkit.getPluginCommand("bang").setTabCompleter(executor);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        boolean isFuck = FuckCommand.isContain(player.getName().toLowerCase());
        boolean isMagic = MagicCommand.isContain(player.getName().toLowerCase());
        boolean isBless = BlessCommand.isContain(player.getName().toLowerCase());
        boolean isSupajp = SupajpCommand.isContain(player.getName().toLowerCase());
        if (isFuck) {
            player.setHealth(0);
            player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&cFucking bad guys!"), "Fuck!", 10, 70, 20);
        }

        if (isMagic) {
            if (!player.hasPotionEffect(PotionEffectType.UNLUCK)) {
                List<PotionEffect> magicList = new ArrayList<>();
                int maxTime = Integer.MAX_VALUE;
                player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&cEnjoying this Feeling!"), "Evade!", 10, 70, 20);

                //霉运
                magicList.add(new PotionEffect(PotionEffectType.UNLUCK, maxTime, 4));
                //恶心
                magicList.add(new PotionEffect(PotionEffectType.CONFUSION, maxTime, 4));
                //缓慢
                magicList.add(new PotionEffect(PotionEffectType.SLOW, maxTime, 4));
                //挖掘疲劳
                magicList.add(new PotionEffect(PotionEffectType.SLOW_DIGGING, maxTime, 4));
                //失明
                /*            magicList.add(new PotionEffect(PotionEffectType.BLINDNESS, maxTime, 4));*/
                //虚弱
                magicList.add(new PotionEffect(PotionEffectType.WEAKNESS, maxTime, 4));

                player.addPotionEffects(magicList);
            }
        }

        if(isBless){
            if (!player.hasPotionEffect(PotionEffectType.SATURATION)) {
                List<PotionEffect> blessList = new ArrayList<>();
                int maxTime = Integer.MAX_VALUE;
                //饱和
                blessList.add(new PotionEffect(PotionEffectType.SATURATION, maxTime, 4));
                //海豚恩惠
                blessList.add(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, maxTime, 4));
                //抗火
                blessList.add(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, maxTime, 4));
                //抗性
                blessList.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, maxTime, 4));
                //生命恢复
                blessList.add(new PotionEffect(PotionEffectType.REGENERATION, maxTime, 4));
                //力量
                blessList.add(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, maxTime, 4));
                //夜视
                blessList.add(new PotionEffect(PotionEffectType.NIGHT_VISION, maxTime, 0));

                player.addPotionEffects(blessList);
            }
        }

        if(isSupajp && !player.hasPotionEffect(PotionEffectType.SATURATION)){
            player.setAllowFlight(true);
            player.setWalkSpeed(0.6f);
            player.setFlySpeed(0.3f);
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(!OfflinePlayersConfig.isContain(player.getName())){
            OfflinePlayersConfig.append(player.getName(), player);
        }
    }


    @Override
    public void onDisable() {

    }

    /*public void logToFile(String msg) {
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
    }*/
}
