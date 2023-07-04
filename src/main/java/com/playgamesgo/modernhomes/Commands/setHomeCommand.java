package com.playgamesgo.modernhomes.Commands;

import com.playgamesgo.modernhomes.ModernHomes;
import com.playgamesgo.modernhomes.utils.ConfigStrings;
import com.playgamesgo.modernhomes.utils.DatabaseManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.sql.SQLException;
import java.util.ArrayList;

public class setHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 1) {
                int currentHomes;
                try {
                    currentHomes = DatabaseManager.connection.createStatement().executeQuery("SELECT COUNT(*) FROM homes WHERE uuid='" + player.getUniqueId() + "'").getInt(1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                int maxHomes = 0;
                for (PermissionAttachmentInfo permissionAttachmentInfo : player.getEffectivePermissions()) {
                    if (!permissionAttachmentInfo.getPermission().startsWith("modernhomes.sethome.max.")) continue;
                    if (permissionAttachmentInfo.getPermission().equals("modernhomes.sethome.max.unlimited")) break;
                    maxHomes = Integer.parseInt(permissionAttachmentInfo.getPermission().toLowerCase().replaceAll("modernhomes.sethome.max.", ""));
                }

                if (currentHomes >= maxHomes && !player.hasPermission("modernhomes.sethome.max.unlimited")) {
                    player.sendMessage(ConfigStrings.tooMuchHomes);
                    return true;
                }

                if (!player.hasPermission("modernhomes.sethome")) {
                    player.sendMessage(ConfigStrings.noPermission);
                    return true;
                }

                for (String s : ModernHomes.config.getStringList("blacklisted-worlds")) {
                    if (player.getWorld().getName().equals(s)) {
                        player.sendMessage(ConfigStrings.blacklistedWorld);
                        return true;
                    }
                }

                try {
                    if (DatabaseManager.connection.createStatement().executeQuery("SELECT COUNT(*) FROM homes WHERE uuid='" + player.getUniqueId() + "' AND name='" + args[0] + "'").getInt(1) > 0) {
                        player.sendMessage(ConfigStrings.homeNameTaken);
                        return true;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }


                try {
                    DatabaseManager.connection.createStatement().executeUpdate("INSERT INTO homes (uuid, name, world, x, y, z, yaw, pitch, public) VALUES ('" + player.getUniqueId() + "', '" + args[0] + "', '" + player.getWorld().getName() + "', '" + player.getLocation().getX() + "', '" + player.getLocation().getY() + "', '" + player.getLocation().getZ() + "', '" + player.getLocation().getYaw() + "', '" + player.getLocation().getPitch() + " ', 'false')");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                player.sendMessage(ConfigStrings.setHome.replace("%home%", args[0]));

            } else {
                player.sendMessage(ConfigStrings.noArgsSetHome);
            }
        } else {
            sender.sendMessage(ConfigStrings.onlyPlayers);
        }

        return true;
    }
}
