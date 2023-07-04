package com.playgamesgo.modernhomes.Commands;

import com.playgamesgo.modernhomes.ModernHomes;
import com.playgamesgo.modernhomes.utils.ConfigStrings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class homeIgnoreCommand implements CommandExecutor {
    public static HashMap<Player, Boolean> ignoreMap = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (ModernHomes.config.getBoolean("home-invite-enabled")) {
                if (ignoreMap.containsKey(player)) {
                    ignoreMap.remove(player);
                    player.sendMessage(ConfigStrings.homeInviteEnabled);
                } else {
                    ignoreMap.put(player, true);
                    player.sendMessage(ConfigStrings.homeInviteDisabled);
                }
            } else {
                player.sendMessage(ConfigStrings.homeInviteGlobalDisabled);
            }
        }
        return true;
    }
}
