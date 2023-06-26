package com.github.onyzn.utils.bungee.cmd.whitelist;

import com.github.onyzn.utils.Manager;
import com.github.onyzn.utils.bungee.cmd.SubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

public class ToggleWhitelistCommand extends SubCommand {

  public ToggleWhitelistCommand() {
    super("toggle", "toggle", "Ativar/desativar whitelist.", false);
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    if (Manager.toggleWhitelist()) {
      sender.sendMessage(new TextComponent("§eA whitelist foi ativada!"));

      ProxyServer.getInstance().getPlayers().forEach(p -> {
        if (Manager.whitelistedPlayers.stream().noneMatch(p.getName()::equalsIgnoreCase)) {
          p.disconnect(new TextComponent(Manager.WHITELIST_MESSAGE));
        }
      });
    } else {
      sender.sendMessage(new TextComponent("§eA whitelist foi desativada!"));
    }
  }
}
