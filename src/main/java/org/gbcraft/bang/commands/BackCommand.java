package org.gbcraft.bang.commands;

import org.bukkit.command.CommandSender;
import org.gbcraft.bang.Bang;

public class BackCommand extends MFCommand {
    public BackCommand(Bang plugin, CommandSender sender, String[] args) {
        super(plugin, sender, args);
        if(args.length >= 3){
            args[2] = args[2].toLowerCase();
        }
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
            else if(args[1].equalsIgnoreCase("supajp")){
                boolean res = SupajpCommand.remove(args[2]);
                if (!res){
                    sender.sendMessage("玩家不在线!");
                }
            }
            else{
                msg = "Command Error!";
            }

            sender.sendMessage(msg);

        }
        return true;
    }
}
