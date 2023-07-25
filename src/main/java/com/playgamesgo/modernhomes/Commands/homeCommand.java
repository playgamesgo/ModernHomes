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
import java.sql.SQLException;

public class homeCommand implements CommandExecutor {
    Plugin plugin;
    Location homeLocation;
    Location playerLocation;
    Player player;
    int delay;
    int scheduler;


    public homeCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            this.player = ((Player) sender).getPlayer();
            if (args.length == 1) {
                if (player.hasPermission("modernhomes.home")) {
                    execute(player, args, false);
                } else {
                    player.sendMessage(ConfigStrings.noPermission);
                }
            } else {
                player.sendMessage(ConfigStrings.noArgsHome);
            }
        } else {
            sender.sendMessage(ConfigStrings.onlyPlayers);
        }

        return true;
    }

    public void execute(Player player, String[] args, boolean oneMode) {
        this.player = player;
        this.playerLocation = player.getLocation();
        try {
            ResultSet rs = DatabaseManager.connection.createStatement().executeQuery("SELECT * FROM homes WHERE uuid = '" + player.getUniqueId() + "' AND name = '" + args[0] + "'");
            if (rs.next()) {
                this.homeLocation = new Location(Bukkit.getWorld(rs.getString("world")), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getFloat("yaw"), rs.getFloat("pitch"));
                this.delay = ModernHomes.config.getInt("teleport-delay") * 20;
                if (player.hasPermission("modernhomes.bypass.delay")) {
                    this.delay = 0;
                }
                this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (homeCommand.this.delay % 20 == 0 && homeCommand.this.delay > 0) {
                            if (homeCommand.this.delay == 20) {
                                homeCommand.this.player.sendMessage(ConfigStrings.teleportInSecond);
                            } else {
                                homeCommand.this.player.sendMessage(ConfigStrings.teleportInSeconds.replace("%seconds%", String.valueOf(delay / 20)));
                            }
                        }

                        if (homeCommand.this.player.getLocation().distance(homeCommand.this.playerLocation) >= 1.0D
                                && !player.hasPermission("modernhomes.bypass.move")
                                && ModernHomes.config.getBoolean("cancel-teleport-on-move")) {
                            Bukkit.getScheduler().cancelTask(homeCommand.this.scheduler);
                            homeCommand.this.player.sendMessage(ConfigStrings.teleportCancelled);
                            return;
                        }
                        if (homeCommand.this.delay == 0) {
                            Bukkit.getScheduler().cancelTask(homeCommand.this.scheduler);
                            homeCommand.this.player.teleport(homeCommand.this.homeLocation);
                        }
                        homeCommand.this.delay--;
                    }
                }, 0L, 1L);
            } else {
                if (!oneMode) {
                    player.sendMessage(ConfigStrings.homeNotFound);
                } else {
                    player.sendMessage(ConfigStrings.oneModeHomeNotFound);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
