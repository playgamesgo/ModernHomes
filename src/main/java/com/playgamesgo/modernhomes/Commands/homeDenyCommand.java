package com.playgamesgo.modernhomes.Commands;

import com.playgamesgo.modernhomes.utils.ConfigStrings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class homeDenyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            execute(player);
        } else {
            sender.sendMessage(ConfigStrings.onlyPlayers);
        }
        return true;
    }

    public static void execute(Player player) {
        if (homeInviteCommand.homeInvite.containsKey(player)) {
            homeInviteCommand.homeInvite.remove(player);
            player.sendMessage(ConfigStrings.homeInviteDenied);
        } else {
            player.sendMessage(ConfigStrings.noHomeInvite);
        }
    }
}
