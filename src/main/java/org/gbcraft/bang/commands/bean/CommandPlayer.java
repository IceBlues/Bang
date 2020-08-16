package org.gbcraft.bang.commands.bean;

import org.apache.commons.lang.StringUtils;

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

    public CommandPlayer(String playerName, String[] description) {
        this.playerName = playerName;
        this.description = new ArrayList<>(Arrays.asList(description));
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

    public String getName() {
        return playerName;
    }

    public void appendDesc(String desc) {
        if (StringUtils.isNotBlank(desc) && !isDesc(desc)) {
            this.description.add(desc);
        }
    }

    public boolean isDesc(String description) {
        return this.description.contains(description);
    }
}
