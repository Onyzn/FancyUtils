package com.github.onyzn.utils.bukkit.cmd.whitelist;

import com.github.onyzn.utils.Manager;
import com.github.onyzn.utils.bukkit.cmd.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ToggleWhitelistCommand extends SubCommand {

  public ToggleWhitelistCommand() {
    super("toggle", "toggle", "Ativar/desativar whitelist.", false);
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    if (Manager.toggleWhitelist()) {
      sender.sendMessage("§eA whitelist foi ativada!");

      Bukkit.getOnlinePlayers().forEach(p -> {
        if (!Manager.isWhitelisted(p.getName())) {
          p.kickPlayer(Manager.WHITELIST_MESSAGE);
        }
      });
    } else {
      sender.sendMessage("§eA whitelist foi desativada!");
    }
  }
}
