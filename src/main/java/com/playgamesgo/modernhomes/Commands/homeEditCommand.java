package com.playgamesgo.modernhomes.Commands;

import com.playgamesgo.modernhomes.utils.ConfigStrings;
import com.playgamesgo.modernhomes.utils.DatabaseManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class homeEditCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                if (args.length > 1) {
                    execute(player, args, false);
                } else {
                    player.sendMessage(ConfigStrings.noArgsOption);
                }
            } else {
                player.sendMessage(ConfigStrings.noArgsEditHome);
            }
        } else {
            sender.sendMessage(ConfigStrings.onlyPlayers);
        }

        return true;
    }

    public static void execute(Player player, String[] args, boolean oneMode) {
        try {
            ResultSet rs = DatabaseManager.connection.createStatement().executeQuery("SELECT * FROM homes WHERE name = '" + args[0] + "' AND uuid = '" + player.getUniqueId() + "'");
            if (rs.next()) {
                if (Objects.equals(args[1], "name")) {
                    if (args.length > 2) {
                        DatabaseManager.connection.createStatement().executeUpdate("UPDATE homes SET name = '" + args[2] + "' WHERE name = '" + args[0] + "' AND uuid = '" + player.getUniqueId() + "'");
                        player.sendMessage(ConfigStrings.homeNameChanged.replace("%new%", args[2]).replace("%old%", args[0]));
                    } else {
                        player.sendMessage(ConfigStrings.noArgsName);
                    }
                } else if (Objects.equals(args[1], "public")) {
                    if (args[2].equals("true") || args[2].equals("false")) {
                        DatabaseManager.connection.createStatement().executeUpdate("UPDATE homes SET public = '" + args[2] + "' WHERE name = '" + args[0] + "' AND uuid = '" + player.getUniqueId() + "'");
                        if (args[2].equals("true")) {
                            if (!oneMode) {
                                player.sendMessage(ConfigStrings.homePublicChangedToTrue.replace("%home%", args[0]));
                            } else {
                                player.sendMessage(ConfigStrings.oneModeHomePublicChangedToTrue);
                            }
                        } else {
                            if (!oneMode) {
                                player.sendMessage(ConfigStrings.homePublicChangedToFalse.replace("%home%", args[0]));
                            } else {
                                player.sendMessage(ConfigStrings.oneModeHomePublicChangedToFalse);
                            }
                        }
                    } else {
                        player.sendMessage(ConfigStrings.noSuchOption);
                    }
                } else {
                    player.sendMessage(ConfigStrings.noSuchOption);
                }
            } else {
                player.sendMessage(ConfigStrings.homeNotFound);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
