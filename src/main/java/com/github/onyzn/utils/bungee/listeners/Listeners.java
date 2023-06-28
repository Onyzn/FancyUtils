package com.github.onyzn.utils.bungee.listeners;

import com.github.onyzn.utils.Manager;
import com.github.onyzn.utils.bungee.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.UUID;

public class Listeners implements Listener {

  public static void setupListeners() {
    ProxyServer.getInstance().getPluginManager().registerListener(Main.getInstance(), new Listeners());
  }

  @EventHandler
  public void onProxyPing(ProxyPingEvent evt) {
    ServerPing ping = evt.getResponse();
    ping.setDescriptionComponent(new TextComponent(Manager.whitelistEnabled ? Manager.WHITELIST_MOTD : Manager.NORMAL_MOTD));
    evt.setResponse(ping);
  }

  @EventHandler
  public void onPlayerLogin(LoginEvent evt) {
    if (!Manager.whitelistEnabled) {
      return;
    }

    PendingConnection player = evt.getConnection();

    if (!Manager.isWhitelisted(player.getName())) {
      evt.setCancelled(true);
      evt.setCancelReason(new TextComponent(Manager.WHITELIST_MESSAGE));
    }
  }
}
