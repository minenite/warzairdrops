package net.minenite.warzairdrops;

import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import net.minenite.warzairdrops.InventorySerializer;
import net.minenite.warzairdrops.SendInfoMessages;
import net.minenite.warzairdrops.SupplyDropsDataManager;


public class CommandEditSupplyDrop
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
                openEditor(p, args[0]);
            } else {
                SendInfoMessages.sendInfoMessage(p, "InsufficientPermissions");
            }
        }
        return false;
    }


    public void openEditor(Player p, String supplyDropName) {
        Inventory inv = Bukkit.createInventory(null, 27, "�9�lEditor: �1�l" + supplyDropName);

        if (SupplyDropsDataManager.getSupplyDropsData().getString("drops." + supplyDropName) != null) {
            try {
                Inventory loadedDropInv =
                        InventorySerializer.fromBase64(SupplyDropsDataManager.getSupplyDropsData().getString("drops." + supplyDropName));
                ItemStack[] dropInvItems = loadedDropInv.getContents();
                for (int i = 0; i < dropInvItems.length; i++) {
                    if (dropInvItems[i] != null) {
                        inv.setItem(i, dropInvItems[i]);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        p.openInventory(inv);
    }



    public void saveSupplyDrop(String dropName, Inventory inv) {
        SupplyDropsDataManager.getSupplyDropsData().set("drops." + dropName,
                InventorySerializer.inventoryToBase64(inv));
        SupplyDropsDataManager.saveSupplyDropsData();
    }


    @EventHandler
    public void leave(InventoryCloseEvent e) {
        Player p = (Player)e.getPlayer();
        String editorName = ChatColor.stripColor(e.getInventory().getName());
        if (editorName.startsWith("Editor:")) {
            String[] parts = editorName.split(":");
            String dropName = parts[1];
            SendInfoMessages.sendInfoMessage(p, "SavedDrop", dropName.trim(), "");
            saveSupplyDrop(dropName.trim(), e.getInventory());
        }
    }
}
