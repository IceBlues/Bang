package org.gbcraft.bang.particle;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public abstract class Effect {
    protected ParticleManager particle;
    protected BukkitTask task;

    public abstract void doIt(Player player);
}
