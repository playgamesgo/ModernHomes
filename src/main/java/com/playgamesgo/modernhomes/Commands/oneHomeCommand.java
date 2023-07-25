package com.playgamesgo.modernhomes.Commands;

import com.playgamesgo.modernhomes.utils.ConfigStrings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class oneHomeCommand implements CommandExecutor {
    Plugin plugin;
    homeAcceptCommand homeAcceptCommand;
    homeCommand homeCommand;
    homeInviteCommand homeInviteCommand;
    homeTpCommand homeTpCommand;

    public oneHomeCommand(Plugin plugin, homeAcceptCommand homeAcceptCommand, homeCommand homeCommand, homeInviteCommand homeInviteCommand, homeTpCommand homeTpCommand) {
        this.plugin = plugin;
        this.homeAcceptCommand = homeAcceptCommand;
        this.homeCommand = homeCommand;
        this.homeInviteCommand = homeInviteCommand;
        this.homeTpCommand = homeTpCommand;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            String[] playerArg = {player.getName()};
            if (args.length == 0) {

                if (player.hasPermission("modernhomes.home")) {
                    homeCommand.execute(player, playerArg, true);
                } else {
                    player.sendMessage(ConfigStrings.noPermission);
                }

            } else {

                if (args[0].equalsIgnoreCase("set")) {
                    setHomeCommand.execute(player, playerArg, true);
                }

                if (args[0].equalsIgnoreCase("remove")) {
                    delHomeCommand.execute(player, playerArg, true);
                }

                if (args[0].equalsIgnoreCase("invite")) {
                    if (args.length > 1) {
                        String[] executeArg = {args[1], player.getName()};
                        homeInviteCommand.execute(player, executeArg, true);
                    } else {
                        player.sendMessage(ConfigStrings.noArgsPlayer);
                    }
                }

                if (args[0].equalsIgnoreCase("accept")) {
                    homeAcceptCommand.execute(sender);
                }

                if (args[0].equalsIgnoreCase("deny")) {
                    homeDenyCommand.execute(player);
                }

                if (args[0].equalsIgnoreCase("ignore")) {
                    homeIgnoreCommand.execute(player);
                }

                if (args[0].equalsIgnoreCase("tp")) {
                    if (args.length > 1) {
                        String[] executeArg = {args[1], player.getName()};
                        homeTpCommand.execute(player, executeArg);
                    } else {
                        player.sendMessage(ConfigStrings.noArgsPlayer);
                    }
                }

                if (args[0].equalsIgnoreCase("public")) {
                    if (args.length > 1) {
                        String[] executeArg = {player.getName(), "public", args[1]};
                        homeEditCommand.execute(player, executeArg, true);
                    } else {
                        player.sendMessage(ConfigStrings.noArgsOption);
                    }
                }

                if (args[0].equalsIgnoreCase("admin")) {
                    if (player.hasPermission("modernhomes.admin")) {
                        if (args.length > 1) {
                            if (args[1].equalsIgnoreCase("delete")) {
                                if (args.length > 2) {
                                    String[] executeArg = {args[2], args[2], "delete"};
                                    homeAdminCommand.execute(sender, executeArg, true);
                                } else {
                                    player.sendMessage(ConfigStrings.noArgsPlayer);
                                }
                            } else if (args[1].equalsIgnoreCase("tp")) {
                                String[] executeArg = {args[2], args[2], "tp"};
                                homeAdminCommand.execute(sender, executeArg, true);
                            } else if (args[1].equalsIgnoreCase("public")) {
                                if (args.length > 2) {
                                    String[] executeArg = {args[2], args[2], "public", args[3]};
                                    homeAdminCommand.execute(sender, executeArg, true);
                                } else {
                                    player.sendMessage(ConfigStrings.noArgsOption);
                                }
                            } else {
                                player.sendMessage(ConfigStrings.noSuchOption);
                            }
                        } else {
                            player.sendMessage(ConfigStrings.noArgsOption);
                        }
                    } else {
                        player.sendMessage(ConfigStrings.noPermission);
                    }
                }
            }
        } else {
            sender.sendMessage(ConfigStrings.onlyPlayers);
        }
        return true;
    }
}
