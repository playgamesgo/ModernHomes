package com.playgamesgo.modernhomes.Commands;

import com.playgamesgo.modernhomes.utils.ConfigStrings;
import com.playgamesgo.modernhomes.utils.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class homeAdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("modernhomes.admin")) {
            if (args.length > 0) {
                if (args.length > 1) {
                    if (args.length > 2) {
                        execute(sender, args, false);
                    } else {
                        sender.sendMessage(ConfigStrings.noArgsOption);
                    }
                } else {
                    sender.sendMessage(ConfigStrings.noArgsAdminHome);
                }
            } else {
                sender.sendMessage(ConfigStrings.noArgsPlayer);
            }
        } else {
            sender.sendMessage(ConfigStrings.noPermission);
        }
        return true;
    }

    public static void execute(CommandSender sender, String[] args, boolean oneMode) {
        Player target = Bukkit.getOfflinePlayer(args[0]).getPlayer();
        if (target != null) {
            try {
                ResultSet rs = DatabaseManager.connection.createStatement().executeQuery("SELECT * FROM homes WHERE uuid = '" + target.getUniqueId() + "' AND name = '" + args[1] + "'");
                if (args[2].equalsIgnoreCase("name")) {
                    if (args.length > 3) {
                        if (rs.next()) {
                            DatabaseManager.connection.createStatement().executeUpdate("UPDATE homes SET name = '" + args[3] + "' WHERE uuid = '" + target.getUniqueId() + "' AND name = '" + args[1] + "'");
                            sender.sendMessage(ConfigStrings.homeNameChanged.replace("%new%", args[3]).replace("%old%", args[1]));
                        } else {
                            sender.sendMessage(ConfigStrings.homeNotFound);
                        }
                    } else {
                        sender.sendMessage(ConfigStrings.noArgsName);
                    }
                } else if (args[2].equalsIgnoreCase("delete")) {
                    if (rs.next()) {
                        DatabaseManager.connection.createStatement().executeUpdate("DELETE FROM homes WHERE uuid = '" + target.getUniqueId() + "' AND name = '" + args[1] + "'");
                        sender.sendMessage(ConfigStrings.homeDeleted.replace("%home%", args[1]));
                    } else {
                        sender.sendMessage(ConfigStrings.homeNotFound);
                    }
                } else if (args[2].equalsIgnoreCase("tp")) {
                    if (rs.next()) {
                        Location homeLocation = new Location(Bukkit.getWorld(rs.getString("world")), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getFloat("yaw"), rs.getFloat("pitch"));
                        if (sender instanceof Player player) {
                            player.teleport(homeLocation);
                        } else {
                            sender.sendMessage(ConfigStrings.onlyPlayers);
                        }
                    } else {
                        sender.sendMessage(ConfigStrings.homeNotFound);
                    }
                } else if (args[2].equalsIgnoreCase("public")) {
                    if (rs.next()) {
                        if (args.length > 3) {
                            if (args[3].equalsIgnoreCase("true")) {
                                DatabaseManager.connection.createStatement().executeUpdate("UPDATE homes SET public = 'true' WHERE uuid = '" + target.getUniqueId() + "' AND name = '" + args[1] + "'");
                                sender.sendMessage(ConfigStrings.homePublicChangedToTrue.replace("%home%", args[1]));
                            } else if (args[3].equalsIgnoreCase("false")) {
                                DatabaseManager.connection.createStatement().executeUpdate("UPDATE homes SET public = 'false' WHERE uuid = '" + target.getUniqueId() + "' AND name = '" + args[1] + "'");
                                sender.sendMessage(ConfigStrings.homePublicChangedToFalse.replace("%home%", args[1]));
                            } else {
                                sender.sendMessage(ConfigStrings.noSuchOption);
                            }
                        } else {
                            sender.sendMessage(ConfigStrings.noArgsOption);
                        }
                    } else {
                        sender.sendMessage(ConfigStrings.homeNotFound);
                    }
                } else {
                    sender.sendMessage(ConfigStrings.noSuchOption);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            sender.sendMessage(ConfigStrings.noSuchPlayer);
        }
    }
}
