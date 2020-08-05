package org.gbcraft.bang.commands;

import org.bukkit.command.CommandSender;
import org.gbcraft.bang.Bang;

public class BackCommand extends MFCommand {
    public BackCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if (args.length >= 3) {
            String node;
            if (args[1].equalsIgnoreCase("fuck")) {
                node = FuckCommand.remove(args[2]);
            }
            else if (args[1].equalsIgnoreCase("magic")) {
                node = MagicCommand.remove(args[2]);
            }
            else if (args[1].equalsIgnoreCase("supajp")) {
                node = SupajpCommand.remove(args[2]);
            }
            else if (args[1].equalsIgnoreCase("freeze")) {
                node = FreezeCommand.remove(args[2]);
            }
            else if (args[1].equalsIgnoreCase("dead")) {
                node = DeadCommand.remove(args[2]);
            }
            else if (args[1].equalsIgnoreCase("all")) {
                FuckCommand.remove(args[2]);
                MagicCommand.remove(args[2]);
                SupajpCommand.remove(args[2]);
                FreezeCommand.remove(args[2]);
                node = "info.remove.success";
            }
            else {
                node = "info.command.back";
            }

            plugin.sendMessage(sender, node);
        }
        else {
            plugin.sendMessage(sender, "info.command.back");
        }

        return true;
    }
}
