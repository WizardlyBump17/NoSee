package com.wizardlybump17.nosee.implementation;

import com.wizardlybump17.nosee.api.VanishablePlayer;
import org.bukkit.Bukkit;

public class DefaultVanishablePlayer implements VanishablePlayer {

    private final String name;
    private boolean vanished;

    public DefaultVanishablePlayer(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setVanished(boolean vanished) {
        this.vanished = vanished;
        Bukkit.getLogger().info("Vanish mode of " + getName() + " changed to " + vanished);
    }

    @Override
    public boolean isVanished() {
        return vanished;
    }
}
