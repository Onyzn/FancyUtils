package com.github.onyzn.utils.bukkit.listeners;

import com.github.onyzn.utils.Manager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.ServerListPingEvent;
import com.github.onyzn.utils.bukkit.objects.SlimePad;
import com.github.onyzn.utils.bukkit.Main;

public class Listeners implements Listener {

  public static void setupListeners() {
    Bukkit.getPluginManager().registerEvents(new Listeners(), Main.getInstance());
  }

  @EventHandler
  public void onServerListPing(ServerListPingEvent evt) {
    evt.setMotd(Manager.whitelistEnabled ? Manager.WHITELIST_MOTD : Manager.NORMAL_MOTD);
  }

  @EventHandler
  public void onBlockBreak(BlockBreakEvent evt) {
    Block block = evt.getBlock();
    if (block.getType() == Material.SLIME_BLOCK) {
      SlimePad pad = SlimePad.getByLocation(block.getLocation());
      if (pad != null) {
        SlimePad.removeSlimePad(pad);
      }
    }
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent evt) {
    Player player = evt.getPlayer();
    Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
    if (block.getType() == Material.SLIME_BLOCK) {
      SlimePad pad = SlimePad.getByLocation(block.getLocation());
      if (pad != null && player.getGameMode() != GameMode.CREATIVE) {
        pad.pushPlayer(player);
      }
    }
  }

  @EventHandler
  public void onPlayerLogin(PlayerLoginEvent evt) {
    if (!Manager.whitelistEnabled) {
      return;
    }

    Player player = evt.getPlayer();
    if (!Manager.isWhitelisted(player.getName())) {
      evt.disallow(PlayerLoginEvent.Result.KICK_BANNED, Manager.WHITELIST_MESSAGE);
    }
  }
}
