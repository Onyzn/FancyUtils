package com.github.onyzn.utils.bukkit.cmd.whitelist;

import com.github.onyzn.utils.Manager;
import org.bukkit.command.CommandSender;
import com.github.onyzn.utils.bukkit.cmd.SubCommand;
import com.github.onyzn.utils.bukkit.Main;

import java.util.List;

public class AddWhitelistCommand extends SubCommand {

  public AddWhitelistCommand() {
    super("add", "add [jogador]", "Adicionar jogador.", false);
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    if (args.length == 0) {
      sender.sendMessage("§cUtilize /whitelist " + this.getUsage());
      return;
    }

    String name = args[0].toLowerCase();
    if (Manager.isWhitelisted(name)) {
      sender.sendMessage("§cEste jogador já está na whitelist.");
      return;
    }

    Manager.addToWhitelist(name);
    sender.sendMessage("§aAdicionado com sucesso!");
  }
}
