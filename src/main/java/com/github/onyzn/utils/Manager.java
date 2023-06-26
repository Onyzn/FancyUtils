package com.github.onyzn.utils;

import tk.fancystore.noisier.plugin.CorePlugin;
import tk.fancystore.noisier.plugin.config.NConfig;
import tk.fancystore.noisier.utils.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class Manager {

  private static CorePlugin plugin;

  public static NConfig CONFIG;
  public static String NORMAL_MOTD;
  public static String WHITELIST_MOTD;
  public static String WHITELIST_MESSAGE;

  public static NConfig WHITELIST;
  public static boolean whitelistEnabled;
  public static List<String> whitelistedPlayers;

  public static void setupManager(CorePlugin plugin) {
    Manager.plugin = plugin;

    CONFIG = plugin.getConfig("config");
    NORMAL_MOTD = StringUtils.formatColors(CONFIG.getString("motd")).replace("\\n", "\n");
    WHITELIST_MOTD = StringUtils.formatColors(CONFIG.getString("whitelist.motd")).replace("\\n", "\n");
    WHITELIST_MESSAGE = StringUtils.formatColors(CONFIG.getString("whitelist.message")).replace("\\n", "\n");

    WHITELIST = plugin.getConfig("whitelist");
    whitelistEnabled = WHITELIST.getBoolean("enabled");
    whitelistedPlayers = WHITELIST.getStringList("players").stream().map(String::toLowerCase).collect(Collectors.toList());
  }

  public static boolean toggleWhitelist() {
    WHITELIST.set("enabled", !whitelistEnabled);
    return whitelistEnabled = !whitelistEnabled;
  }

  public static boolean isWhitelisted(String playerName) {
    return whitelistedPlayers.contains(playerName);
  }

  public static void addToWhitelist(String playerName) {
    whitelistedPlayers.add(playerName);
    WHITELIST.set("players", whitelistedPlayers);
  }

  public static void removeFromWhitelist(String playerName) {
    whitelistedPlayers.remove(playerName);
    WHITELIST.set("players", whitelistedPlayers);
  }

  public static CorePlugin getPlugin() {
    return plugin;
  }
}
