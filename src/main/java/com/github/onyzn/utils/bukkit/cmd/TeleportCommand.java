package com.github.onyzn.utils.bukkit.cmd;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TeleportCommand extends Commands {

  public TeleportCommand() {
    super("teleport", "tp");
  }

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (sender instanceof ConsoleCommandSender) {
      sender.sendMessage("§cApenas jogadores podem executar este comando.");
      return;
    }

    if (!sender.hasPermission("fancyutils.cmd.teleport")) {
      sender.sendMessage("§cVocê não possui permissão para utilizar este comando.");
      return;
    }

    Player player = (Player) sender;

    if (args.length == 0 || args.length == 2) {
      sender.sendMessage("§cUtilize /" + label + " [jogador] ou /" + label + " [x] [y] [z] [jogador]");
      return;
    }

    if (args.length == 1) {
      Player target = Bukkit.getPlayerExact(args[0]);
      if (target == null) {
        sender.sendMessage("§cEste jogador está offline.");
        return;
      }

      player.teleport(target);
      return;
    }

    double x, y, z;
    try {
      x = Double.parseDouble(args[0].replace(",", "."));
      y = Double.parseDouble(args[1].replace(",", "."));
      z = Double.parseDouble(args[2].replace(",", "."));
    } catch (NumberFormatException ignored) {
      sender.sendMessage("§cNúmeros inválidos.");
      return;
    }

    Player target = player;
    if (args.length > 3) {
      target = Bukkit.getPlayerExact(args[3]);
    }

    if (target == null) {
      sender.sendMessage("§cEste jogador está offline.");
      return;
    }

    target.teleport(new Location(target.getWorld(), x, y, z, target.getLocation().getYaw(), target.getLocation().getPitch()));
    sender.sendMessage((target.getName().equals(sender.getName()) ?
        "§aVocê foi teleportado para " + x + " " + y + " " + z + "!" :
        "§aVocê teleportou " + target.getName() + " para " + x + " " + y + " " + z + "!"
        ).replace(".0", "")
    );
  }

  @Override
  public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
    if (sender instanceof Player) {
      Player player = (Player) sender;

      if (args.length == 1) {
        List<String> list = new ArrayList<>();
        list.add(Double.toString(player.getLocation().getX()).split("\\.")[0]);
        list.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
        return list;
      }

      if (args.length == 2) {
        return Collections.singletonList(Double.toString(player.getLocation().getY()).split("\\.")[0]);
      }

      if (args.length == 3) {
        return Collections.singletonList(Double.toString(player.getLocation().getZ()).split("\\.")[0]);
      }

      return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

    return null;
  }
}
