package com.github.onyzn.utils.bukkit.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class TeleportHereCommand extends Commands {
  public TeleportHereCommand() {
    super("teleporthere", "tphere");
  }

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (sender instanceof ConsoleCommandSender) {
      sender.sendMessage("§cApenas jogadores podem executar este comando.");
      return;
    }

    if (!sender.hasPermission("fancyutils.cmd.teleporthere")) {
      sender.sendMessage("§cVocê não possui permissão para utilizar este comando.");
      return;
    }

    if (args.length == 0) {
      sender.sendMessage("§cUtilize /" + label + " [jogador]");
      return;
    }

    Player target = Bukkit.getPlayerExact(args[0]);
    if (target == null) {
      sender.sendMessage("§cEste jogador está offline.");
      return;
    }

    sender.sendMessage("§aVocê teleportou o jogador " + target.getName() + " até você.");
    target.teleport((Player) sender);
  }
}
