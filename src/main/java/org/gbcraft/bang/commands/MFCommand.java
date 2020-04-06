package org.gbcraft.bang.commands;

import org.bukkit.command.CommandSender;
import org.gbcraft.bang.Bang;

public abstract class MFCommand {
    protected Bang plugin;
    protected CommandSender sender;
    protected String[] args;

    public MFCommand(Bang plugin, CommandSender sender, String[] args) {
        this.plugin = plugin;
        this.sender = sender;
        this.args = args;
    }

    public abstract boolean run();
}
