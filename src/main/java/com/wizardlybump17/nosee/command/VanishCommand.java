package com.wizardlybump17.nosee.command;

import com.wizardlybump17.nosee.NoSee;
import com.wizardlybump17.nosee.api.VanishablePlayer;
import com.wizardlybump17.nosee.api.manager.VanishablePlayerManager;
import com.wizardlybump17.nosee.implementation.DefaultVanishablePlayer;
import com.wizardlybump17.wlib.command.WCommand;
import com.wizardlybump17.wlib.config.WConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

public class VanishCommand extends WCommand {

    public VanishCommand(JavaPlugin plugin) {
        super(plugin, "vanish");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        NoSee noSee = (NoSee) plugin;
        VanishablePlayerManager vanishablePlayerManager = noSee.getVanishablePlayerManager();
        WConfig config = noSee.getMessagesConfig();

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("§cCommand available only for players!");
                return true;
            }

            Player player = (Player) sender;

            VanishablePlayer vanishablePlayer = vanishablePlayerManager.getRegisteredPlayer(player.getName());

            if (vanishablePlayer == null) {
                vanishablePlayer = new DefaultVanishablePlayer(player.getName());
                if (player.hasMetadata("vanishMode")) vanishablePlayer.setVanished(true);

                vanishablePlayerManager.registerPlayer(vanishablePlayer);
            }

            vanishablePlayer.setVanished(!vanishablePlayer.isVanished());

            if (vanishablePlayer.isVanished()) {
                player.setMetadata("vanishMode", new FixedMetadataValue(plugin, null));
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) onlinePlayer.hidePlayer(player);
            } else {
                player.removeMetadata("vanishMode", plugin);
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) onlinePlayer.showPlayer(player);
            }

            player.sendMessage(noSee.getMessages().get("vanish.player")
                    .replace("{new_status}",
                            vanishablePlayer.isVanished()
                                    ? noSee.getMessages().get("vanish.enabled")
                                    : noSee.getMessages().get("vanish.disabled")));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "set": {
                Player target = Bukkit.getPlayerExact(args[1]);
                if (target == null) {
                    sender.sendMessage("§cInvalid player!");
                    return true;
                }

                VanishablePlayer vanishablePlayer = vanishablePlayerManager.getRegisteredPlayer(target.getName());

                if (vanishablePlayer == null) {
                    vanishablePlayer = new DefaultVanishablePlayer(target.getName());
                    if (target.hasMetadata("vanishMode")) vanishablePlayer.setVanished(true);

                    vanishablePlayerManager.registerPlayer(vanishablePlayer);
                }

                vanishablePlayer.setVanished(!vanishablePlayer.isVanished());

                if (vanishablePlayer.isVanished()) {
                    target.setMetadata("vanishMode", new FixedMetadataValue(plugin, null));
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) onlinePlayer.hidePlayer(target);
                } else {
                    target.removeMetadata("vanishMode", plugin);
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) onlinePlayer.showPlayer(target);
                }

                sender.sendMessage(noSee.getMessages().get("vanish.target.sender")
                        .replace("{target}", target.getName())
                        .replace("{new_status}",
                                vanishablePlayer.isVanished()
                                        ? noSee.getMessages().get("vanish.enabled")
                                        : noSee.getMessages().get("vanish.disabled")));
                target.sendMessage(noSee.getMessages().get("vanish.target.target")
                        .replace("{sender}", sender.getName())
                        .replace("{new_status}",
                                vanishablePlayer.isVanished()
                                        ? noSee.getMessages().get("vanish.enabled")
                                        : noSee.getMessages().get("vanish.disabled")));
                return true;
            }

            case "reload": {
                config.reloadConfig();

                sender.sendMessage(noSee.getMessages().get("reloaded"));
                return true;
            }

            default: {
                sender.sendMessage(noSee.getMessages().get("vanish.invalid-args"));
                return true;
            }
        }
    }
}
