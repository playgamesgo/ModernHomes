package com.playgamesgo.modernhomes.utils;

import com.playgamesgo.modernhomes.ModernHomes;
import org.bukkit.ChatColor;

public class ConfigStrings {
    public static final String pluginPrefix = ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.plugin-prefix")) + " ";
    public static final String onlyPlayers = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.only-players"));
    public static final String noArgsSetHome = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.no-args-set-home"));
    public static final String noArgsDelHome = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.no-args-del-home"));
    public static final String noArgsHome = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.no-args-home"));
    public static final String noArgsEditHome = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.no-args-edit-home"));
    public static final String setHome = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.set-home"));
    public static final String tooMuchHomes = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.too-much-homes"));
    public static final String noPermission = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.no-permission"));
    public static final String homeNameTaken = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-name-taken"));
    public static final String homeNotFound = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-not-found"));
    public static final String blacklistedWorld = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.blacklisted-world"));
    public static final String teleportCancelled = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.teleport-cancelled"));
    public static final String teleportInSeconds = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.teleport-in-seconds"));
    public static final String teleportInSecond = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.teleport-in-second"));
    public static final String homeDeleted = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-deleted"));
    public static final String homeList = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-list"));
    public static final String homeListEmpty = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-list-empty"));
    public static final String homeListOther = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-list-other"));
    public static final String homeListEmptyOther = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-list-empty-other"));
    public static final String noSuchPlayer = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.no-such-player"));
    public static final String noArgsOption = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.no-args-option"));
    public static final String noSuchOption = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.no-such-option"));
    public static final String homeNameChanged = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-name-changed"));
    public static final String homePublicChangedToTrue = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-public-changed-to-true"));
    public static final String homePublicChangedToFalse = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-public-changed-to-false"));
    public static final String noArgsPlayer = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.no-args-player"));
    public static final String noArgsTpHome = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.no-args-tp-home"));
    public static final String homeNotPublic = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-not-public"));
    public static final String homeInviteMessage = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-invite-message"));
    public static final String homeInviteSent = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-invite-sent"));
    public static final String homeInviteExpired = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-invite-expired"));
    public static final String homeInviteEnabled = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-invite-enabled"));
    public static final String homeInviteDisabled = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-invite-disabled"));
    public static final String homeInviteGlobalDisabled = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-invite-global-disabled"));
    public static final String PlayerIgnoreHomeInvite = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.player-ignore-home-invite"));
    public static final String noHomeInvite = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.no-home-invite"));
    public static final String homeInviteDenied = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.home-invite-denied"));
    public static final String noArgsName = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.no-args-name"));
    public static final String noArgsAdminHome = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.no-args-admin-home"));
    public static final String configReloaded = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.config-reloaded"));
    public static final String helpHeader = pluginPrefix + ChatColor.translateAlternateColorCodes('&',
            ModernHomes.config.getString("messages.help-header"));
}
