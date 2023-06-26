package com.github.onyzn.utils.bukkit.cmd;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import com.github.onyzn.utils.bukkit.objects.SlimePad;

public class SlimePadCommand extends Commands {

  public SlimePadCommand() {
    super("sp");
  }

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (sender instanceof ConsoleCommandSender) {
      sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
      return;
    }

    Player player = (Player) sender;
    if (!player.hasPermission("fancyutils.cmd.slimepad")) {
      player.sendMessage("§cVocê não possui permissão para utilizar este comando.");
      return;
    }

    Block block = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
    Location location = block.getLocation().clone();
    location.setDirection(player.getLocation().getDirection());

    SlimePad pad = SlimePad.getByLocation(location);
    if (pad == null) {
      block.setType(Material.SLIME_BLOCK);
      SlimePad.addSlimePad(location);
      player.sendMessage("§aSlime adicionado com sucesso.");
    } else {
      SlimePad.removeSlimePad(pad);
      player.sendMessage("§cSlime removido com sucesso.");
    }
  }
}
