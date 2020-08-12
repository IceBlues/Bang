package org.gbcraft.bang.commands.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandPlayer {
    String playerName;
    List<String> description;

    public CommandPlayer(String playerName) {
        this.playerName = playerName;
        this.description = new ArrayList<>();
    }

    public CommandPlayer(String playerName, String[] description){
        this.playerName = playerName;
        this.description = Arrays.asList(description);
    }

    public CommandPlayer(String playerName, List<String> description) {
        this.playerName = playerName;
        this.description = description;
    }

    public CommandPlayer(String playerName, String description) {
        this.playerName = playerName;
        this.description = new ArrayList<>();
        this.description.add(description);
    }

    public String getName(){
        return playerName;
    }

    public boolean isDesc(String description){
        return this.description.contains(description);
    }
}
