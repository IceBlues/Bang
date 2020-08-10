package org.gbcraft.bang.commands.bean;

import org.gbcraft.bang.commands.MFCommand;

public enum CommandName {
    BANCHAT, DEAD, FREEZE, FUCK, MAGIC, SUPAJP;
    public Class<MFCommand> CommandClass(){
        String name = this.name();
        Class<MFCommand> res = null;
        try {
            res = (Class<MFCommand>) Class.forName("org.gbcraft.bang.commands."+ Character.toString(name.charAt(0)).toUpperCase() + name.substring(1).toLowerCase() + "Command");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return res;
    }
}
