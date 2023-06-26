package com.github.onyzn.utils.bungee.cmd.whitelist;

import com.github.onyzn.utils.Manager;
import com.github.onyzn.utils.bungee.cmd.SubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class AddWhitelistCommand extends SubCommand {

  public AddWhitelistCommand() {
    super("add", "add [jogador]", "Adicionar jogador.", false);
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    if (args.length == 0) {
      sender.sendMessage(new TextComponent("§cUtilize /whitelist " + this.getUsage()));
      return;
    }

    String name = args[0].toLowerCase();
    if (Manager.isWhitelisted(name)) {
      sender.sendMessage(new TextComponent("§cEste jogador já está na whitelist."));
      return;
    }

    Manager.addToWhitelist(name);
    sender.sendMessage(new TextComponent("§aAdicionado com sucesso!"));
  }
}
