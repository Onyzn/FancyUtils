package com.github.onyzn.utils.bukkit.cmd;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class UpCommand extends Commands {

  public UpCommand() {
    super("up");
  }

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (sender instanceof ConsoleCommandSender) {
      sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
      return;
    }

    Player player = (Player) sender;
    if (!player.hasPermission("fancyutils.cmd.up")) {
      player.sendMessage("§cVocê não possui permissão para utilizar este comando.");
      return;
    }

    int up = 1;
    if (args.length > 0) {
      try {
        /*if (args[0].startsWith("-")) {
          throw new NullPointerException();
        }*/

        up = Integer.parseInt(args[0]);
      } catch (NumberFormatException ex) {
        player.sendMessage("§cUtilize números válidos.");
      }
    }

    Block block = player.getLocation().clone().add(0, up, 0).getBlock();

    block.setType(Material.GLASS);
    //noinspection deprecation
    block.setData((byte) 0);

    player.teleport(player.getLocation().add(0, up + 1, 0));
  }
}
