package com.github.onyzn.utils.bukkit.vanish;

import com.github.onyzn.utils.bukkit.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import tk.fancystore.noisier.bukkit.nms.NMS;
import tk.fancystore.noisier.bukkit.player.Profile;

import java.util.ArrayList;
import java.util.List;

public class VanishManager implements Listener {

  public static List<Player> VANISHED = new ArrayList<>();

  public static void setupManager() {
    new BukkitRunnable() {
      @Override
      public void run() {
        VANISHED.forEach(player -> {
          Bukkit.getOnlinePlayers().forEach(target -> {
            if (!target.hasPermission("fancyutks.cmd.vanish")) {
              target.hidePlayer(player);
            }
          });

          NMS.sendActionBar(player, "§cVocê está escondido dos jogadores.");
        });
      }
    }.runTaskTimer(Main.getInstance(), 0, 5);
  }

  public static void vanish(Player player) {
    VANISHED.add(player);
  }

  public static void unvanish(Player player) {
    VANISHED.remove(player);

    Profile profile = Profile.getProfile(player.getName());
    if (profile.playingGame()) {
      profile.getGame().listPlayers().forEach(target -> target.getPlayer().showPlayer(player));
      return;
    }

    for (Profile targetProfile : Profile.listProfiles()) {
      if (targetProfile.playingGame()) {
        continue;
      }

      targetProfile.getPlayer().showPlayer(player);
    }
  }
}

