package org.gbcraft.bang.commands.bean;

public enum BanChatType {
    ALL, MESSAGE, LOGS, REPLACE;

    public String type() {
        return "banchat_" + this.name().toLowerCase();
    }

    public static BanChatType fromSubCommand(String cmd) {
        BanChatType type = MESSAGE;
        try {
            type = BanChatType.valueOf(cmd.toUpperCase());
        }
        catch (Exception ignored) {
        }
        return type;
    }
}
