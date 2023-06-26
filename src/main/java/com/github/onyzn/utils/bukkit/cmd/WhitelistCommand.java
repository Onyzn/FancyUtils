package com.github.onyzn.utils.bukkit.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import com.github.onyzn.utils.bukkit.cmd.whitelist.AddWhitelistCommand;
import com.github.onyzn.utils.bukkit.cmd.whitelist.RemoveWhitelistCommand;
import com.github.onyzn.utils.bukkit.cmd.whitelist.StatusWhitelistCommand;
import com.github.onyzn.utils.bukkit.cmd.whitelist.ToggleWhitelistCommand;

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
  public void perform(CommandSender sender, String label, String[] args) {
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
        if (sender instanceof ConsoleCommandSender) {
          sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
          return;
        }

        subCommand.perform((Player) sender, list.toArray(new String[list.size()]));
      } else {
        subCommand.perform(sender, list.toArray(new String[list.size()]));
      }
    }
  }

  private void sendHelp(CommandSender sender, int page) {
    List<SubCommand> commands = this.commands.stream().filter(subcommand -> sender instanceof Player || !subcommand.onlyForPlayer()).collect(Collectors.toList());
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
      sender.sendMessage("§cPágina não encontrada.");
      return;
    }

    sb.append(" ");
    sender.sendMessage(sb.toString());
  }
}