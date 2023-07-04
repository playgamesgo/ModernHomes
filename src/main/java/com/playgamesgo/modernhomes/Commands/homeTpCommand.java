package com.playgamesgo.modernhomes.Commands;

import com.playgamesgo.modernhomes.ModernHomes;
import com.playgamesgo.modernhomes.utils.ConfigStrings;
import com.playgamesgo.modernhomes.utils.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;

public class homeTpCommand implements CommandExecutor {
    Plugin plugin;
    Location homeLocation;
    Location playerLocation;
    Player player;
    int delay;
    int scheduler;
    public homeTpCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            player = (Player) sender;
            if (player.hasPermission("modernhomes.home.other.tp")) {
                if (args.length > 0) {
                    if (args.length > 1) {
                        if (Bukkit.getOfflinePlayer(args[0]).getPlayer() != null) {
                            Player target = Bukkit.getOfflinePlayer(args[0]).getPlayer();
                            try {
                                ResultSet resultSet = DatabaseManager.connection.createStatement().executeQuery("SELECT * FROM homes WHERE uuid='" + target.getUniqueId() + "' AND name='" + args[1] + "'");
                                if (resultSet.next()) {
                                    if (resultSet.getString("public").equals("true")) {
                                        this.homeLocation = new Location(Bukkit.getWorld(resultSet.getString("world")), resultSet.getDouble("x"), resultSet.getDouble("y"), resultSet.getDouble("z"), resultSet.getFloat("yaw"), resultSet.getFloat("pitch"));
                                        this.delay = ModernHomes.config.getInt("teleport-delay") * 20;
                                        this.playerLocation = player.getLocation();
                                        if (player.hasPermission("modernhomes.bypass.delay")) {
                                            this.delay = 0;
                                        }
                                        this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                                            @Override
                                            public void run() {
                                                if (homeTpCommand.this.delay % 20 == 0 && homeTpCommand.this.delay > 0) {
                                                    if (homeTpCommand.this.delay == 20) {
                                                        homeTpCommand.this.player.sendMessage(ConfigStrings.teleportInSecond);
                                                    } else {
                                                        homeTpCommand.this.player.sendMessage(ConfigStrings.teleportInSeconds.replace("%seconds%", String.valueOf(delay / 20)));
                                                    }
                                                }

                                                if (homeTpCommand.this.player.getLocation().distance(homeTpCommand.this.playerLocation) >= 1.0D
                                                        && !player.hasPermission("modernhomes.bypass.move")
                                                        && ModernHomes.config.getBoolean("cancel-teleport-on-move")) {
                                                    Bukkit.getScheduler().cancelTask(homeTpCommand.this.scheduler);
                                                    homeTpCommand.this.player.sendMessage(ConfigStrings.teleportCancelled);
                                                    return;
                                                }
                                                if (homeTpCommand.this.delay == 0) {
                                                    Bukkit.getScheduler().cancelTask(homeTpCommand.this.scheduler);
                                                    homeTpCommand.this.player.teleport(homeTpCommand.this.homeLocation);
                                                }
                                                homeTpCommand.this.delay--;
                                            }
                                        }, 0L, 1L);
                                    } else {
                                        player.sendMessage(ConfigStrings.homeNotPublic);
                                    }
                                } else {
                                    player.sendMessage(ConfigStrings.homeNotFound);
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            player.sendMessage(ConfigStrings.noSuchPlayer);
                        }
                    } else {
                        player.sendMessage(ConfigStrings.noArgsTpHome);
                    }
                } else {
                    player.sendMessage(ConfigStrings.noArgsPlayer);
                }
            } else {
                player.sendMessage(ConfigStrings.noPermission);
            }
        } else {
            sender.sendMessage(ConfigStrings.onlyPlayers);
        }
        return true;
    }
}
