package com.github.onyzn.utils.bungee.cmd.whitelist;

import com.github.onyzn.utils.Manager;
import com.github.onyzn.utils.bungee.cmd.SubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import tk.fancystore.noisier.role.Role;
import tk.fancystore.noisier.utils.StringUtils;

import java.util.List;

public class StatusWhitelistCommand extends SubCommand {

  public StatusWhitelistCommand() {
    super("status", "status", "Mostrar status da whitelist.", false);
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    sender.sendMessage(new TextComponent(" \nEstado: " + (Manager.whitelistEnabled ? "§aAtivado" : "§cDesativado")));
    List<String> players = Manager.whitelistedPlayers;

    if (!players.isEmpty()) {
      sender.sendMessage(new TextComponent("§fJogadores:"));
      players.forEach(p ->
          sender.sendMessage(StringUtils.toTextComponent("§f ▪ " + Role.getPrefixed(p) + " </append>§4[x]</actions>hover:show_text>§7Clique aqui para remover este jogador.</and>click:run_command>/whitelist remove " + p)));
    }
    sender.sendMessage(new TextComponent(""));
  }
}
