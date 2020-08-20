package org.gbcraft.bang.particle;

import org.bukkit.Particle;
import org.bukkit.entity.Player;

import java.util.Random;

public class FireBootsEffect extends Effect {
    @Override
    public void doIt(Player player) {
        if (null == particle) {
            particle = new ParticleManager(player.getUniqueId(), EffectType.FIRE_BOOTS, task);
        }
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            player.getWorld().spawnParticle(Particle.LAVA, player.getLocation().clone().add(r.nextDouble() * 0.5, r.nextDouble() * 0.5, r.nextDouble() * 0.5), 5);
        }

        for (int i = 0; i < 5; i++) {
            player.getWorld().spawnParticle(Particle.LAVA, player.getLocation().clone().add((r.nextDouble() * 0.5) * -1, r.nextDouble() * 0.5, (r.nextDouble() * 0.5) * -1), 5);
        }
    }
}
