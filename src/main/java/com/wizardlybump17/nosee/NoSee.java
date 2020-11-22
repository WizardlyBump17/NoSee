package com.wizardlybump17.nosee;

import com.wizardlybump17.nosee.api.manager.VanishablePlayerManager;
import com.wizardlybump17.nosee.command.VanishCommand;
import com.wizardlybump17.nosee.implementation.manager.DefaultVanishablePlayerManager;
import com.wizardlybump17.nosee.listener.PlayerJoinListener;
import com.wizardlybump17.wlib.config.WConfig;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

@Getter
public class NoSee extends JavaPlugin {

    private final VanishablePlayerManager vanishablePlayerManager = new DefaultVanishablePlayerManager();

    private WConfig messagesConfig;

    private final Map<String, String> messages = new HashMap<>();

    @Override
    public void onEnable() {
        new VanishCommand(this);
        new PlayerJoinListener(this);
        messagesConfig = new WConfig(this, "messages.yml", true);
        reloadMessages();
    }

    public void reloadMessages() {
        messagesConfig.reloadConfig();

        messages.put(
                "vanish.enabled",
                messagesConfig.getString("vanish.enabled").replace('&', '§'));
        messages.put(
                "vanish.disabled",
                messagesConfig.getString("vanish.disabled").replace('&', '§'));

        messages.put(
                "vanish.player",
                messagesConfig.getString("vanish.player").replace('&', '§'));
        messages.put(
                "vanish.target.sender",
                messagesConfig.getString("vanish.target.sender").replace('&', '§'));
        messages.put(
                "vanish.target.target",
                messagesConfig.getString("vanish.target.target").replace('&', '§'));

        messages.put(
                "vanish.invalid-args",
                messagesConfig.getString("vanish.invalid-args").replace('&', '§'));

        messages.put(
                "reloaded",
                messagesConfig.getString("reloaded").replace('&', '§'));
    }
}
