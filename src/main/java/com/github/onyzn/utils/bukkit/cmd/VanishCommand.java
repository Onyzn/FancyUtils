package com.github.onyzn.utils.bukkit.cmd;

import com.github.onyzn.utils.bukkit.vanish.VanishManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class VanishCommand extends Commands {

  public VanishCommand() {
    super("vanish");
  }

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (sender instanceof ConsoleCommandSender) {
      sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
      return;
    }

    Player player = (Player) sender;
    if (!player.hasPermission("fancyutils.cmd.vanish")) {
      player.sendMessage("§cVocê não possui permissão para utilizar este comando.");
      return;
    }

    if (VanishManager.VANISHED.contains(player)) {
      VanishManager.unvanish(player);
      player.sendMessage("§cSeu vanish foi desativado.");
    } else {
      VanishManager.vanish(player);
      player.sendMessage("§aSeu vanish foi desativado.");
    }
  }
}
