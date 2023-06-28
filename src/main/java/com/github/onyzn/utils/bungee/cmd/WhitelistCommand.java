package com.github.onyzn.utils.bungee.cmd;

import com.github.onyzn.utils.bungee.cmd.whitelist.*;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.*;
import java.util.stream.Collectors;

public class WhitelistCommand extends Commands {

  private final List<SubCommand> commands = new ArrayList<>();

  public WhitelistCommand() {
    super("whitelist");

    this.commands.add(new ToggleWhitelistCommand());
    this.commands.add(new StatusWhitelistCommand());
    this.commands.add(new AddWhitelistCommand());
    this.commands.add(new RemoveWhitelistCommand());
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    if (!sender.hasPermission("fancyutils.cmd.whitelist")) {
      sender.sendMessage(new TextComponent("§cVocê não possui permissão para utilizar este comando."));
      return;
    }

    if (args.length == 0) {
      this.sendHelp(sender, 1);
      return;
    }

    try {
      this.sendHelp(sender, Integer.parseInt(args[0]));
    } catch (Exception ex) {
      SubCommand subCommand = this.commands.stream().filter(sc -> sc.getName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
      if (subCommand == null) {
        this.sendHelp(sender, 1);
        return;
      }

      List<String> list = new ArrayList<>(Arrays.asList(args));
      list.remove(0);
      if (subCommand.onlyForPlayer()) {
        if (!(sender instanceof ProxiedPlayer)) {
          sender.sendMessage(new TextComponent("§cApenas jogadores podem utilizar este comando."));
          return;
        }

        subCommand.perform((ProxiedPlayer) sender, list.toArray(new String[0]));
      } else {
        subCommand.perform(sender, list.toArray(new String[0]));
      }
    }
  }

  private void sendHelp(CommandSender sender, int page) {
    List<SubCommand> commands = this.commands.stream().filter(subcommand -> sender instanceof ProxiedPlayer || !subcommand.onlyForPlayer()).collect(Collectors.toList());
    Map<Integer, StringBuilder> pages = new HashMap<>();

    int pagesCount = (commands.size() + 6) / 7;
    for (int index = 0; index < commands.size(); index++) {
      int currentPage = (index + 8) / 8;
      if (!pages.containsKey(currentPage)) {
        pages.put(currentPage, new StringBuilder(" \n§eAjuda - " + currentPage + "/" + pagesCount + "\n \n"));
      }

      pages.get(currentPage).append("§6/whitelist ").append(commands.get(index).getUsage()).append(" §f- §7").append(commands.get(index).getDescription()).append("\n");
    }

    StringBuilder sb = pages.get(page);
    if (sb == null) {
      sender.sendMessage(new TextComponent("§cPágina não encontrada."));
      return;
    }

    sb.append(" ");
    sender.sendMessage(new TextComponent(sb.toString()));
  }
}