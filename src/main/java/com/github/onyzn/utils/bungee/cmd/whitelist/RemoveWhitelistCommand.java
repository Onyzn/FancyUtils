package com.github.onyzn.utils.bungee.cmd.whitelist;

import com.github.onyzn.utils.Manager;
import com.github.onyzn.utils.bungee.cmd.SubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class RemoveWhitelistCommand extends SubCommand {

  public RemoveWhitelistCommand() {
    super("remove", "remove [jogador]", "Remover jogador.", false);
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    if (args.length == 0) {
      sender.sendMessage(new TextComponent("§cUtilize /whitelist " + this.getUsage()));
      return;
    }

    String name = args[0].toLowerCase();
    if (!Manager.isWhitelisted(name)) {
      sender.sendMessage(new TextComponent("§cEste jogador não está na whitelist."));
      return;
    }

    sender.sendMessage(new TextComponent("§aJogador removido com sucesso."));
    Manager.removeFromWhitelist(name);
    if (Manager.whitelistEnabled) {
      ProxiedPlayer target = ProxyServer.getInstance().getPlayer(name);
      if (target != null) {
        target.disconnect(new TextComponent(Manager.WHITELIST_MESSAGE));
      }
    }
  }
}
