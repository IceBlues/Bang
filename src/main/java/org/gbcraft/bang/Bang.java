package org.gbcraft.bang;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.gbcraft.bang.commands.FuckCommand;
import org.gbcraft.bang.commands.MFCommandExecutor;
import org.gbcraft.bang.commands.MagicCommand;

import java.util.ArrayList;
import java.util.List;

public final class Bang extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        List
        Bukkit.getPluginManager().registerEvents(this, this);
        MFCommandExecutor executor = new MFCommandExecutor(this);
        saveDefaultConfig();
        Bukkit.getPluginCommand("bang").setExecutor(executor);
        Bukkit.getPluginCommand("bang").setTabCompleter(executor);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        boolean isFuck = FuckCommand.isContain(player.getName());
        boolean isMagic = MagicCommand.isContain(player.getName());
        if (isFuck) {
            player.setHealth(0);
            player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&cFucking bad guys!"), "Fuck!", 10, 70, 20);
        }

        if (isMagic) {
            List<PotionEffect> magicList = new ArrayList<>();
            Integer maxTime = Integer.MAX_VALUE;

            if (!player.hasPotionEffect(PotionEffectType.UNLUCK)) {
                player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&cEnjoying this Feeling!"), "Evade!", 10, 70, 20);
            }

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


    @Override
    public void onDisable() {

    }
}
