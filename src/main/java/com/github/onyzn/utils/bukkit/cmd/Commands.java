package com.github.onyzn.utils.bukkit.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.SimpleCommandMap;
import com.github.onyzn.utils.bukkit.Main;

import java.util.Arrays;
import java.util.logging.Level;

public abstract class Commands extends Command {

  public Commands(String name, String... aliases) {
    super(name);
    this.setAliases(Arrays.asList(aliases));

    try {
      SimpleCommandMap simpleCommandMap = (SimpleCommandMap) Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap").invoke(Bukkit.getServer());
      simpleCommandMap.register(this.getName(), "fancyutils", this);
    } catch (ReflectiveOperationException ex) {
      Main.getInstance().getLogger().log(Level.SEVERE, "Cannot register command: ", ex);
    }
  }

  public static void setupCommands() {
    new FlyCommand();
    new LobbyCommand();
    new SlimePadCommand();
    new StaffChatCommand();
    new UpCommand();
    new WhitelistCommand();
    new GamemodeCommand();
    new TeleportCommand();
    new TeleportHereCommand();
    new VanishCommand();
  }

  public abstract void perform(CommandSender sender, String label, String[] args);

  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    this.perform(sender, commandLabel, args);
    return true;
  }
}
