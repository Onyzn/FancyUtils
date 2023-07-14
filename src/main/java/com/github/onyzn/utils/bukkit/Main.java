package com.github.onyzn.utils.bukkit;

import com.github.onyzn.utils.Manager;
import com.github.onyzn.utils.bukkit.cmd.Commands;
import com.github.onyzn.utils.bukkit.listeners.Listeners;
import com.github.onyzn.utils.bukkit.objects.SlimePad;
import com.github.onyzn.utils.bukkit.vanish.VanishManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.SimpleCommandMap;
import tk.fancystore.noisier.bukkit.plugin.NBukkit;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Main extends NBukkit {

  private static Main instance;

  @Override
  public void start() {}

  @Override
  public void load() {
    instance = this;
  }

  @Override
  public void enable() {
    Commands.setupCommands();
    Listeners.setupListeners();
    SlimePad.setupSlimePads();
    VanishManager.setupManager();
    Manager.setupManager(this);

    // desativar comandos redundantes
    Bukkit.getScheduler().runTask(this, () -> {
      try {
        String regex = Arrays.stream(Bukkit.getPluginManager().getPlugins()).map(pl -> '|' + pl.getName()).collect(Collectors.joining("", "(?iu)(spigot|bukkit|minecraft", "):.*"));

        SimpleCommandMap commandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
        Field field = commandMap.getClass().getDeclaredField("knownCommands");
        field.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, Command> knownCommands = (Map<String, Command>) field.get(commandMap);

        List<String> commands = Manager.CONFIG.getStringList("unregister-commands");
        commands.forEach(knownCommands::remove);

        int oldSize = knownCommands.size();
        new HashMap<>(knownCommands).keySet().stream().filter(key -> key.matches(regex)).forEach(knownCommands::remove);

        this.getLogger().info("Removidos " + commands.size() + " comandos!");
        this.getLogger().info("Removidos " + (oldSize - knownCommands.size()) + " fallbacks!");
      } catch (Exception ex) {
        this.getLogger().log(Level.SEVERE, "Could not remove commands and fallbacks!", ex);
      }
    });

    this.getLogger().info("O plugin foi ativado.");
  }

  @Override
  public void disable() {
    this.getLogger().info("O plugin foi desativado.");
  }

  public static Main getInstance() {
    return instance;
  }
}
