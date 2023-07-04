package com.playgamesgo.modernhomes.Commands;

import com.playgamesgo.modernhomes.utils.ConfigStrings;
import com.playgamesgo.modernhomes.utils.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class homesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                if (player.hasPermission("modernhomes.home")) {
                    try {
                        ResultSet rs = DatabaseManager.connection.createStatement().executeQuery("SELECT * FROM homes WHERE uuid = '" + player.getUniqueId() + "'");
                        StringBuilder homes = new StringBuilder();
                        while (rs.next()) {
                            homes.append(rs.getString("name")).append(", ");
                        }
                        if (homes.length() > 0) {
                            player.sendMessage(ConfigStrings.homeList.replace("%homes%", homes.substring(0, homes.length() - 2)));
                        } else {
                            player.sendMessage(ConfigStrings.homeListEmpty);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    player.sendMessage(ConfigStrings.noPermission);
                }
            } else {
                if (player.hasPermission("modernhomes.home.other.see")) {
                    try {
                        Player target = Bukkit.getOfflinePlayer(args[0]).getPlayer();
                        if (target == null) {
                            player.sendMessage(ConfigStrings.noSuchPlayer);
                            return true;
                        }
                        ResultSet rs = DatabaseManager.connection.createStatement().executeQuery("SELECT * FROM homes WHERE uuid = '" + target.getUniqueId() + "'");
                        StringBuilder homes = new StringBuilder();
                        while (rs.next()) {
                            homes.append(rs.getString("name")).append(", ");
                        }
                        if (homes.length() > 0) {
                            player.sendMessage(ConfigStrings.homeListOther.replace("%homes%", homes.substring(0, homes.length() - 2)).replace("%player%", args[0]));
                        } else {
                            player.sendMessage(ConfigStrings.homeListEmptyOther.replace("%player%", args[0]));
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    player.sendMessage(ConfigStrings.noPermission);
                }
            }
        } else {
            sender.sendMessage(ConfigStrings.onlyPlayers);
        }
        return true;
    }
}
