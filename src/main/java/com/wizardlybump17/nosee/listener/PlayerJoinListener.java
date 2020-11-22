package com.wizardlybump17.nosee.listener;

import com.wizardlybump17.nosee.NoSee;
import com.wizardlybump17.nosee.api.VanishablePlayer;
import com.wizardlybump17.nosee.api.manager.VanishablePlayerManager;
import com.wizardlybump17.nosee.implementation.DefaultVanishablePlayer;
import com.wizardlybump17.wlib.listener.WListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerJoinListener extends WListener {

    public PlayerJoinListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        NoSee noSee = (NoSee) plugin;
        VanishablePlayerManager vanishablePlayerManager = noSee.getVanishablePlayerManager();

        Player player = event.getPlayer();

        for (VanishablePlayer registeredPlayer : vanishablePlayerManager.getRegisteredPlayers().values()) {
            if (!registeredPlayer.isVanished()) continue;

            Player target = Bukkit.getPlayerExact(registeredPlayer.getName());
            if (target == null) continue;
            player.hidePlayer(target);
        }

        if (!player.hasMetadata("vanishMode")) return;

        VanishablePlayer vanishablePlayer = vanishablePlayerManager.getRegisteredPlayer(player.getName());

        if (vanishablePlayer == null) {
            vanishablePlayer = new DefaultVanishablePlayer(player.getName());
            vanishablePlayer.setVanished(true);

            vanishablePlayerManager.registerPlayer(vanishablePlayer);
        }

        player.sendMessage(noSee.getMessages().get("vanish.player")
                .replace("{new_status}",
                        vanishablePlayer.isVanished()
                                ? noSee.getMessages().get("vanish.enabled")
                                : noSee.getMessages().get("vanish.disabled")));

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) onlinePlayer.hidePlayer(player);
    }
}
