package org.gbcraft.bang.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.gbcraft.bang.Bang;
import org.gbcraft.bang.particle.EffectType;
import org.gbcraft.bang.particle.FireBootsEffect;
import org.gbcraft.bang.particle.ParticleManager;

public class DoubleRocketPunchCommand extends MFCommand {

    public DoubleRocketPunchCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if (args.length > 1) {
            Player player = Bukkit.getPlayer(args[1]);
            if (null != player) {
                new FireBootsEffect().doIt(player);

                player.setVelocity(new Vector(0, 500, 0));
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    player.setVelocity(new Vector(0, -700, 0));
                    ParticleManager.remove(player.getUniqueId(), EffectType.FIRE_BOOTS);
                }, 40);
            }
        }
        return true;
    }
}
