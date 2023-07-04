package com.playgamesgo.modernhomes.Commands;

import com.playgamesgo.modernhomes.ModernHomes;
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
import java.util.HashMap;

public class homeInviteCommand implements CommandExecutor {
    public static HashMap<Player, Integer> homeInvite = new HashMap<>();
    int scheduler;
    int delay;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                if (args.length > 1) {
                    if (ModernHomes.config.getBoolean("home-invite-enabled")) {
                        try {
                            ResultSet rs = DatabaseManager.connection.createStatement().executeQuery("SELECT * FROM homes WHERE uuid = '" + player.getUniqueId() + "' AND name = '" + args[1] + "'");
                            if (rs.next()) {
                                Player target = player.getServer().getPlayer(args[0]);
                                if (target == null) {
                                    player.sendMessage(ConfigStrings.noSuchPlayer);
                                } else {
                                    homeInvite.remove(target);
                                    if (homeIgnoreCommand.ignoreMap.containsKey(target)) {
                                        player.sendMessage(ConfigStrings.PlayerIgnoreHomeInvite);
                                        return true;
                                    }
                                    homeInvite.put(target, rs.getInt("id"));
                                    target.sendMessage(ConfigStrings.homeInviteMessage.replace("%player%", player.getName()).replace("%home%", args[1]));
                                    player.sendMessage(ConfigStrings.homeInviteSent.replace("%player%", target.getName()).replace("%home%", args[1]));

                                    this.delay = ModernHomes.config.getInt("home-invite-expire-time") * 20;
                                    this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(Bukkit.getPluginManager().getPlugin("ModernHomes"), new Runnable() {
                                        @Override
                                        public void run() {
                                            if (homeInvite.containsKey(target)) {
                                                if (homeInviteCommand.this.delay == 0) {
                                                    homeInvite.remove(target);
                                                    target.sendMessage(ConfigStrings.homeInviteExpired);
                                                    player.sendMessage(ConfigStrings.homeInviteExpired);
                                                    Bukkit.getScheduler().cancelTask(homeInviteCommand.this.scheduler);
                                                }
                                            } else {
                                                Bukkit.getScheduler().cancelTask(homeInviteCommand.this.scheduler);
                                            }
                                            homeInviteCommand.this.delay--;
                                        }
                                    }, 0, 1);
                                }
                            } else {
                                player.sendMessage(ConfigStrings.homeNotFound);
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        player.sendMessage(ConfigStrings.homeInviteGlobalDisabled);
                    }
                } else {
                    player.sendMessage(ConfigStrings.noArgsHome);
                }
            } else {
                player.sendMessage(ConfigStrings.noArgsPlayer);
            }
        } else {
            sender.sendMessage(ConfigStrings.onlyPlayers);
        }
        return true;
    }
}
