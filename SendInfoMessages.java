package net.minenite.warzairdrops;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import net.minenite.warzairdrops.Main;


public class SendInfoMessages
{
    public static void sendInfoMessage(Player p, String messageConfigPath, String dropName, String envoyName) {
        String msg = Main.getPlugin().getConfig().getString("Messages." + messageConfigPath);
        msg = msg.replaceAll("%dropName%", dropName);
        msg = msg.replaceAll("%envoyName%", envoyName);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }


    public static void sendInfoMessage(Player p, String messageConfigPath) {
        String msg = Main.getPlugin().getConfig().getString("Messages." + messageConfigPath);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }
}
