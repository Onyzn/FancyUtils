package com.github.onyzn.utils.bukkit.cmd;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GamemodeCommand extends Commands {

  public GamemodeCommand() {
    super("gamemode", "gm");
  }

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("fancyutils.cmd.gamemode")) {
      sender.sendMessage("§cVocê não possui permissão para executar este comando.");
      return;
    }

    if (args.length < 1 || sender instanceof ConsoleCommandSender && args.length == 1) {
      sender.sendMessage("§cUtilize /" + label + " [modo] [jogador]");
      return;
    }

    Player target = Bukkit.getPlayerExact(sender.getName());
    if (args.length > 1) {
      target = Bukkit.getPlayerExact(args[1]);
    }

    if (target == null) {
      sender.sendMessage("§cEste jogador está offline.");
      return;
    }

    GameMode gm = null;
    try {
      //noinspection deprecation
      gm = GameMode.getByValue(Integer.parseInt(args[0]));
    } catch (NumberFormatException ignored) {
      try {
        gm = GameMode.valueOf(args[0].toUpperCase());
      } catch (IllegalArgumentException ignore) {}
    }

    if (gm == null) {
      sender.sendMessage("§cModo de jogo inválido! Utilize números de 0 à 3 como modo de jogo.");
      return;
    }

    target.setGameMode(gm);
    sender.sendMessage(target.getName().equals(sender.getName()) ?
        "§aSeu modo de jogo foi alterado para " + gm.name() + "!" :
        "§aVocê alterou o modo de jogo de " + target.getName() + " para " + gm.name() + "!");
  }

  @Override
  public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
    if (args.length == 1) {
      return Arrays.stream(GameMode.values()).map(Enum::name).collect(Collectors.toList());
    }

    return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
  }
}
