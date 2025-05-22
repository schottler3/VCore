package friends.friendcoreplugin.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Msg {
    public static void send(CommandSender sender, String message){
        send(sender, message, "&a");
    }

    public static void send(CommandSender sender, String message, String prefix){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message));
    }

    public static void send(CommandSender sender, String message, String prefix, String message2, String prefix2){
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + message) + ChatColor.translateAlternateColorCodes('&', prefix2 + message2));
    }
}
