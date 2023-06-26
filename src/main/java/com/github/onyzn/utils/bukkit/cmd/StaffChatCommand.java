package com.github.onyzn.utils.bukkit.cmd;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import tk.fancystore.noisier.bukkit.nms.NMS;
import tk.fancystore.noisier.role.Role;
import tk.fancystore.noisier.utils.StringUtils;
import tk.fancystore.noisier.utils.enums.EnumSound;

public class StaffChatCommand extends Commands {

  public StaffChatCommand() {
    super("sc");
  }

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("fancyutils.cmd.staffchat")) {
      sender.sendMessage("§cVocê não possui permissão para utilizar este comando.");
      return;
    }

    if (args.length == 0) {
      sender.sendMessage("§cUtilize /sc [mensagem]");
      return;
    }

    BaseComponent message = StringUtils.toTextComponent(
        "§d[STAFF] " + Role.getPrefixed(sender.getName()) + "§f: " + StringUtils.formatColors(StringUtils.join(args, " "))  + "</actions>hover:show_text>§eClique para enviar uma mensagem no chat da staff!</and>click:suggest_command>/sc ");
    Bukkit.getOnlinePlayers().forEach(p -> {
      if (p.hasPermission("fancyutils.cmd.staffchat")) {
        p.spigot().sendMessage(message);
        NMS.sendActionBar(p, "§eHá uma nova mensagem no chat da staff!");
        EnumSound.ORB_PICKUP.play(p, p.getLocation(), 1.0f, 2.0f);
      }
    });
  }
}
