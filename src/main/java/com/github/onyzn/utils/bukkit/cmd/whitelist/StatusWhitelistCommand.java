package com.github.onyzn.utils.bukkit.cmd.whitelist;

import com.github.onyzn.utils.Manager;
import com.github.onyzn.utils.bukkit.cmd.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.fancystore.noisier.role.Role;
import tk.fancystore.noisier.utils.StringUtils;

import java.util.List;

public class StatusWhitelistCommand extends SubCommand {

  public StatusWhitelistCommand() {
    super("status", "status", "Mostrar status da whitelist.", false);
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    sender.sendMessage(" \nEstado: " + (Manager.whitelistEnabled ? "§aAtivado" : "§cDesativado") + "");
    if (sender instanceof Player) {
      List<String> players = Manager.whitelistedPlayers;

      if (!players.isEmpty()) {
        sender.sendMessage("§fJogadores:");
        players.forEach(p ->
            ((Player) sender).spigot().sendMessage(StringUtils.toTextComponent("§f ▪ " + Role.getPrefixed(p) + " </append>§4[x]</actions>hover:show_text>§7Clique aqui para remover este jogador.</and>click:run_command>/whitelist remove " + p)));
      }
    }
    sender.sendMessage("");
  }
}
