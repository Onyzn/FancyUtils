package com.github.onyzn.utils.bukkit.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tk.fancystore.noisier.bukkit.player.Profile;
import tk.fancystore.noisier.utils.enums.EnumSound;

public class FlyCommand extends Commands {

  public FlyCommand() {
    super("fly", "voar");
  }

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (sender instanceof ConsoleCommandSender) {
      sender.sendMessage("§cApenas jogadores podem utilizar este comando.");
      return;
    }

    Player player = (Player) sender;
    Profile profile = Profile.getProfile(player.getName());
    if (!player.hasPermission("fancyutils.fly")) {
      player.sendMessage("§cVocê não possui permissão para utilizar este comando.");
      return;
    }

    if (profile.playingGame()) {
      player.sendMessage("§cVocê não pode alterar o modo voar em jogo.");
      return;
    }

    player.setAllowFlight(!player.getAllowFlight());
    player.sendMessage("§eModo voar " + (player.getAllowFlight() ? "ativado." : "desativado."));
    EnumSound.ORB_PICKUP.play(player, 1f, 2f);
  }
}
