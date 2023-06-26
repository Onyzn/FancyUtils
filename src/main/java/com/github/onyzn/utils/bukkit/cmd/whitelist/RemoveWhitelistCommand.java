package com.github.onyzn.utils.bukkit.cmd.whitelist;

import com.github.onyzn.utils.Manager;
import com.github.onyzn.utils.bukkit.cmd.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveWhitelistCommand extends SubCommand {

  public RemoveWhitelistCommand() {
    super("remove", "remove [jogador]", "Remover jogador.", false);
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    if (args.length == 0) {
      sender.sendMessage("§cUtilize /whitelist " + this.getUsage());
      return;
    }

    String name = args[0].toLowerCase();
    if (!Manager.isWhitelisted(name)) {
      sender.sendMessage("§cEste jogador não está na whitelist.");
      return;
    }

    sender.sendMessage("§aJogador removido com sucesso.");
    Manager.removeFromWhitelist(name);
    if (Manager.whitelistEnabled) {
      Player target = Bukkit.getPlayerExact(name);
      if (target != null) {
        target.kickPlayer(Manager.WHITELIST_MESSAGE);
      }
    }
  }
}
