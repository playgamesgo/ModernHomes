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

public class homeAcceptCommand implements CommandExecutor {
    Plugin plugin;
    Location homeLocation;
    Location playerLocation;
    Player player;
    int delay;
    int scheduler;

    public homeAcceptCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            execute(sender);
        } else {
            sender.sendMessage(ConfigStrings.onlyPlayers);
        }
        return true;
    }

    public void execute(CommandSender sender) {
        this.player = ((Player) sender).getPlayer();
        if (homeInviteCommand.homeInvite.containsKey(player)) {
            int homeID = homeInviteCommand.homeInvite.get(player);
            try {
                ResultSet rs = DatabaseManager.connection.createStatement().executeQuery("SELECT * FROM homes WHERE id = " + homeID);
                if (rs.next()) {
                    this.playerLocation = player.getLocation();
                    this.homeLocation = new Location(Bukkit.getWorld(rs.getString("world")), rs.getDouble("x"), rs.getDouble("y"), rs.getDouble("z"), rs.getFloat("yaw"), rs.getFloat("pitch"));
                    this.delay = ModernHomes.config.getInt("teleport-delay") * 20;
                    if (player.hasPermission("modernhomes.bypass.delay")) {
                        this.delay = 0;
                    }
                    this.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
                        @Override
                        public void run() {
                            if (homeAcceptCommand.this.delay % 20 == 0 && homeAcceptCommand.this.delay > 0) {
                                if (homeAcceptCommand.this.delay == 20) {
                                    homeAcceptCommand.this.player.sendMessage(ConfigStrings.teleportInSecond);
                                } else {
                                    homeAcceptCommand.this.player.sendMessage(ConfigStrings.teleportInSeconds.replace("%seconds%", String.valueOf(delay / 20)));
                                }
                            }

                            if (homeAcceptCommand.this.player.getLocation().distance(homeAcceptCommand.this.playerLocation) >= 1.0D
                                    && !player.hasPermission("modernhomes.bypass.move")
                                    && ModernHomes.config.getBoolean("cancel-teleport-on-move")) {
                                Bukkit.getScheduler().cancelTask(homeAcceptCommand.this.scheduler);
                                homeAcceptCommand.this.player.sendMessage(ConfigStrings.teleportCancelled);
                                return;
                            }
                            if (homeAcceptCommand.this.delay == 0) {
                                Bukkit.getScheduler().cancelTask(homeAcceptCommand.this.scheduler);
                                homeAcceptCommand.this.player.teleport(homeAcceptCommand.this.homeLocation);
                            }
                            homeAcceptCommand.this.delay--;
                        }
                    }, 0L, 1L);
                    homeInviteCommand.homeInvite.remove(player);
                } else {
                    player.sendMessage(ConfigStrings.homeNotFound);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            player.sendMessage(ConfigStrings.noHomeInvite);
        }
    }
}
