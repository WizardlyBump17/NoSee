package com.wizardlybump17.nosee.implementation.manager;

import com.wizardlybump17.nosee.api.VanishablePlayer;
import com.wizardlybump17.nosee.api.manager.VanishablePlayerManager;

import java.util.HashMap;
import java.util.Map;

public class DefaultVanishablePlayerManager implements VanishablePlayerManager {

    private final Map<String, VanishablePlayer> players = new HashMap<>();

    @Override
    public Map<String, VanishablePlayer> getRegisteredPlayers() {
        return players;
    }

    @Override
    public VanishablePlayer getRegisteredPlayer(String name) {
        return players.get(name.toLowerCase());
    }

    @Override
    public void unregisterPlayer(String name) {
        players.remove(name.toLowerCase());
    }

    @Override
    public void registerPlayer(VanishablePlayer player) {
        players.put(player.getName().toLowerCase(), player);
    }

    @Override
    public boolean isPlayerRegistered(String name) {
        return players.containsKey(name.toLowerCase());
    }
}
