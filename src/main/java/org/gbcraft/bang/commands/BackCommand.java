package org.gbcraft.bang.commands;

import org.bukkit.command.CommandSender;
import org.gbcraft.bang.Bang;

public class BackCommand extends MFCommand {
    public BackCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
    }

    @Override
    public boolean run() {
        if(sender.hasPermission("bang.base") && args.length >= 2) {
            String msg = "取消成功";
            if(args[1].equalsIgnoreCase("fuck")) {
                FuckCommand.remove(args[2]);
            }
            else if(args[1].equalsIgnoreCase("magic")){
                MagicCommand.remove(args[2]);
            }
            else if(args[1].equalsIgnoreCase("bless")){
                BlessCommand.remove(args[2]);
            }
            else{
                msg = "Command Error!";
            }

            sender.sendMessage(msg);

        }
        return true;
    }
}
