package com.github.onyzn.utils.bukkit.cmd;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import tk.fancystore.noisier.bukkit.Core;
import tk.fancystore.noisier.bukkit.game.FakeGame;
import tk.fancystore.noisier.bukkit.game.Game;
import tk.fancystore.noisier.bukkit.game.GameTeam;
import tk.fancystore.noisier.bukkit.player.Profile;

public class LobbyCommand extends Commands {

  public LobbyCommand() {
    super("lobby");
  }

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (sender instanceof ConsoleCommandSender) {
      sender.sendMessage("§cApenas jogadores podem executar este comando.");
      return;
    }

    Player player = (Player) sender;
    Profile profile = Profile.getProfile(player.getName());
    Game<? extends GameTeam> game = profile.getGame();
    if (game != null && !(game instanceof FakeGame)) {
      player.sendMessage("§aConectando ao Lobby do " + Core.minigame + "...");
      game.leave(profile, null);
      return;
    }

    Core.sendServer(profile, "lobby");
  }
}
