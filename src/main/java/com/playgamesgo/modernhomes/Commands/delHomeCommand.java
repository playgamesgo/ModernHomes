package com.playgamesgo.modernhomes.Commands;

import com.playgamesgo.modernhomes.utils.ConfigStrings;
import com.playgamesgo.modernhomes.utils.DatabaseManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class delHomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                if (player.hasPermission("modernhomes.delhome")) {
                    try {
                        ResultSet rs = DatabaseManager.connection.createStatement().executeQuery("SELECT * FROM homes WHERE uuid = '" + player.getUniqueId() + "' AND name = '" + args[0] + "'");
                        if (rs.next()) {
                            DatabaseManager.connection.createStatement().executeUpdate("DELETE FROM homes WHERE uuid = '" + player.getUniqueId() + "' AND name = '" + args[0] + "'");
                            player.sendMessage(ConfigStrings.homeDeleted.replace("%home%", args[0]));
                        } else {
                            player.sendMessage(ConfigStrings.homeNotFound);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    player.sendMessage(ConfigStrings.noPermission);
                }
            } else {
                player.sendMessage(ConfigStrings.noArgsDelHome);
            }
        } else {
            sender.sendMessage(ConfigStrings.onlyPlayers);
        }
        return true;
    }
}
