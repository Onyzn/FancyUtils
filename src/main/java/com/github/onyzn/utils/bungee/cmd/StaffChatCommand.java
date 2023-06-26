package com.github.onyzn.utils.bungee.cmd;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import tk.fancystore.noisier.role.Role;
import tk.fancystore.noisier.utils.StringUtils;

public class StaffChatCommand extends Commands {

  public StaffChatCommand() {
    super("sc");
  }

  @Override
  public void perform(CommandSender sender, String[] args) {
    if (!sender.hasPermission("fancyutils.cmd.staffchat")) {
      sender.sendMessage(new TextComponent("§cVocê não possui permissão para utilizar este comando."));
      return;
    }

    if (args.length == 0) {
      sender.sendMessage(new TextComponent("§cUtilize /sc [mensagem]"));
      return;
    }

    BaseComponent message = StringUtils.toTextComponent(
        "§d[STAFF] " + Role.getPrefixed(sender.getName()) + "§f: " + StringUtils.formatColors(StringUtils.join(args, " "))  + "</actions>hover:show_text>§eClique para enviar uma mensagem no chat da staff!</and>click:suggest_command>/sc ");
    ProxyServer.getInstance().getPlayers().forEach(p -> {
      if (p.hasPermission("fancyutils.cmd.staffchat")) {
        p.sendMessage(message);
      }
    });
  }
}
