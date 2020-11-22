package com.wizardlybump17.nosee.api.manager;

import com.wizardlybump17.nosee.api.VanishablePlayer;

import java.util.Map;

public interface VanishablePlayerManager {

    Map<String, VanishablePlayer> getRegisteredPlayers();

    VanishablePlayer getRegisteredPlayer(String name);

    void unregisterPlayer(String name);

    void registerPlayer(VanishablePlayer player);

    boolean isPlayerRegistered(String name);
}
