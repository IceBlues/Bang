package org.gbcraft.bang.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.gbcraft.bang.commands.bean.CommandName;
import org.gbcraft.bang.commands.bean.ContainerManager;
import org.gbcraft.bang.particle.EffectType;
import org.gbcraft.bang.particle.FireBootsEffect;
import org.gbcraft.bang.particle.ParticleManager;

import java.util.ArrayList;
import java.util.List;

public class PlayerMoveListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        String pName = player.getName();
        boolean isMagic = ContainerManager.isContain(CommandName.MAGIC, pName);
        boolean isSupajp = ContainerManager.isContain(CommandName.SUPAJP, pName);
        boolean isFreeze = ContainerManager.isContain(CommandName.FREEZE, pName);

        if (isMagic && !player.hasPotionEffect(PotionEffectType.UNLUCK)) {
            magic(player);
        }

        if (isSupajp && player.getWalkSpeed() != 0.6f) {
            supajp(player);
        }
        if (isFreeze) {
            event.setCancelled(true);
        }

        if (ParticleManager.hasEffect(player.getUniqueId(), EffectType.FIRE_BOOTS)) {
            new FireBootsEffect().doIt(player);
        }
    }

    private void supajp(Player player) {
        player.setAllowFlight(true);
        player.setWalkSpeed(0.6f);
        player.setFlySpeed(0.3f);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0));
    }

    private void magic(Player player) {
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
