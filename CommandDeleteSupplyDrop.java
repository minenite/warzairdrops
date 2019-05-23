package net.minenite.warzairdrops;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import net.minenite.warzairdrops.SendInfoMessages;
import net.minenite.warzairdrops.SupplyDropsDataManager;

public class CommandDeleteSupplyDrop
        implements CommandExecutor, Listener
{
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("supplydrop.admin")) {
                if (args.length == 0) {
                    SendInfoMessages.sendInfoMessage(p, "SpecifyDropName");
                    return false;
                }
                String dropName = args[0];

                if (SupplyDropsDataManager.getSupplyDropsData().getString("drops." + dropName) != null) {

                    SupplyDropsDataManager.getSupplyDropsData().set("drops." + dropName, null);
                    SupplyDropsDataManager.saveSupplyDropsData();
                    SendInfoMessages.sendInfoMessage(p, "DeleteDrop", dropName, "");
                } else {
                    SendInfoMessages.sendInfoMessage(p, "SpecifyValidDropName");
                }
            } else {
                SendInfoMessages.sendInfoMessage(p, "InsufficientPermissions");
            }
        }
        return false;
    }
}
