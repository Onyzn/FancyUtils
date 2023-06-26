package com.github.onyzn.utils.bukkit.objects;

import net.minecraft.server.v1_8_R3.EnumParticle;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import tk.fancystore.noisier.plugin.config.NConfig;
import tk.fancystore.noisier.utils.BukkitUtils;
import tk.fancystore.noisier.utils.enums.EnumSound;
import com.github.onyzn.utils.bukkit.Main;
import tk.fancystore.noisier.utils.particles.ParticleEffect;

import java.util.ArrayList;
import java.util.List;

public class SlimePad {

  private final int id;
  private final Location location;
  private final double pushUpwards;
  private final double pushForwards;

  public SlimePad(int id, Location location, double pushUpwards, double pushForwards) {
    this.id = id;
    this.location = location;
    this.pushUpwards = pushUpwards;
    this.pushForwards = pushForwards;
  }

  public int getId() {
    return this.id;
  }

  public Location getLocation() {
    return this.location;
  }

  public void pushPlayer(Player player) {
    Vector direction = this.location.clone().getDirection();

    direction.setY(this.pushUpwards);
    player.setVelocity(direction.multiply(this.pushForwards));

    EnumSound.WITHER_SHOOT.play(player.getWorld(), player.getLocation(), 5.0f, 1.0f);
  }

  private final static NConfig CONFIG = Main.getInstance().getConfig("slimes");
  private final static List<SlimePad> SLIME_PADS = new ArrayList<>();

  public static void setupSlimePads() {
    for (String key : CONFIG.getKeys("slime-pads")) {
      Location location = BukkitUtils.deserializeLocation(CONFIG.getString("slime-pads." + key + ".location"));
     if (!location.getChunk().isLoaded()) {
       location.getChunk().load(true);
     }

      double pushUpwards = CONFIG.getDouble("slime-pads." + key + ".pushUpwards");
      double pushForwards = CONFIG.getDouble("slime-pads." + key + ".pushForwards");

      SLIME_PADS.add(new SlimePad(Integer.parseInt(key), location, pushUpwards, pushForwards));
    }

    // show particles
    new BukkitRunnable() {

      @Override
      public void run() {
        for (SlimePad pad : SLIME_PADS) {
          Location center = pad.getLocation().clone().add(0.5, 1, 0.5);
          ParticleEffect.VILLAGER_HAPPY.display(0.2f, 0, 0.2f, 1, 3, center, 200);
          ParticleEffect.VILLAGER_HAPPY.display(-0.2f, 0, -0.2f, 1, 3, center, 200);
          ParticleEffect.VILLAGER_HAPPY.display(-0.2f, 0, 0.2f, 1, 3, center, 200);
          ParticleEffect.VILLAGER_HAPPY.display(0.2f, 0, -0.2f, 1, 3, center, 200);
        }

      }
    }.runTaskTimer(Main.getInstance(), 0, 40);
  }

  public static void addSlimePad(Location location) {
    addSlimePad(location, 0.5, 2.0);
  }

  public static void addSlimePad(Location location, double pushUpwards, double pushForwards) {
    int id = SlimePad.SLIME_PADS.size() == 0 ? 0 : SlimePad.SLIME_PADS.get(SlimePad.SLIME_PADS.size() - 1).getId() + 1;

    SlimePad.CONFIG.set("slime-pads." + id + ".location", BukkitUtils.serializeLocation(location));
    SlimePad.CONFIG.set("slime-pads." + id + ".pushUpwards", pushUpwards);
    SlimePad.CONFIG.set("slime-pads." + id + ".pushForwards", pushForwards);
    SlimePad.SLIME_PADS.add(new SlimePad(id, location, pushUpwards, pushForwards));
  }

  public static void removeSlimePad(SlimePad pad) {
    SlimePad.CONFIG.set("slime-pads." + pad.getId(), null);
    SlimePad.SLIME_PADS.remove(pad);
  }

  public static SlimePad getByLocation(Location location) {
    return SLIME_PADS.stream().filter(sp ->
      sp.getLocation().getX() == location.getX() && sp.getLocation().getY() == location.getY() && sp.getLocation().getZ() == location.getZ()
    ).findFirst().orElse(null);
  }
}
