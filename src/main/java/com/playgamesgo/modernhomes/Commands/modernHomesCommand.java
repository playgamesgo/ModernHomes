package com.playgamesgo.modernhomes.Commands;

import com.playgamesgo.modernhomes.ModernHomes;
import com.playgamesgo.modernhomes.utils.ConfigStrings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class modernHomesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 0 && args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("modernhomes.admin")) {
                try {
                    ModernHomes.config.reload();
                    ModernHomes.config.save();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                sender.sendMessage(ConfigStrings.configReloaded);
            } else {
                sender.sendMessage(ConfigStrings.noPermission);
            }
        } else {
            sender.sendMessage(ConfigStrings.helpHeader);
            List<String> help = ModernHomes.config.getStringList("messages.help");
            if (ModernHomes.config.getBoolean("one-command-mode")) {
                help = ModernHomes.config.getStringList("one-mode-help");
            }
            for (String line : help) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', line));
            }
        }
        return true;
    }
}
