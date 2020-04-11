package org.gbcraft.bang.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.gbcraft.bang.Bang;

public class UglyfaceCommand extends MFCommand {
    public UglyfaceCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if(sender.hasPermission("bang.base")){
            if (args.length < 2)
                return true;

            Player player = Bukkit.getPlayer(args[1]);
            if (player != null) {
                player.spawnParticle(Particle.MOB_APPEARANCE, player.getLocation(), 1);
                player.playSound(player.getLocation(), Sound.ENTITY_ELDER_GUARDIAN_CURSE, 1F, 0F);
                String msg = plugin.getConfig().getString("warningMsg");
                if (msg != null && !"".equals(msg)) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                }

            }
        }

        return true;
    }
}
