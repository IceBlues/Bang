package org.gbcraft.bang.exception;

public class PluginNotFoundException extends Exception {
    @Override
    public String getMessage() {
        return "Plugin Instance Not Found!";
    }
}
