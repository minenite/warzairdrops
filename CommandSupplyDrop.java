package net.minenite.warzairdrops;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import net.minenite.warzairdrops.Main;
import net.minenite.warzairdrops.DropGlider;
import net.minenite.warzairdrops.SendInfoMessages;
import net.minenite.warzairdrops.SupplyDropsDataManager;

public class CommandSupplyDrop
        implements CommandExecutor
{
    FileConfiguration cfg = Main.getPlugin().getConfig();


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player)sender;
            if (p.hasPermission("supplydrop.use")) {

                if (args.length == 0) {
                    SendInfoMessages.sendInfoMessage(p, "SpecifyDropName");
                    return false;
                }

                String dropName = args[0];

                if (SupplyDropsDataManager.getSupplyDropsData().getString("drops." + dropName) == null) {
                    SendInfoMessages.sendInfoMessage(p, "SpecifyValidDropName");
                    return false;
                }

                if (!p.hasPermission("supplydrop.use." + dropName)) {
                    SendInfoMessages.sendInfoMessage(p, "InsufficientPermissionsPerDrop", dropName, "");
                    return false;
                }

                World curWorld = p.getWorld();
                Location ploc = p.getLocation();


                int locX = (int)((Integer.signum((int)ploc.getX()) == -1.0D) ? (ploc.getX() - 1.0D) : ploc.getX());
                int locY = (int)ploc.getY();
                int locZ = (int)((Integer.signum((int)ploc.getZ()) == -1.0D) ? (ploc.getZ() - 1.0D) : ploc.getZ());

                Location check1 = new Location(curWorld, (locX + 1), locY, locZ);
                Location check2 = new Location(curWorld, locX, locY, (locZ + 1));
                Location check3 = new Location(curWorld, (locX - 1), locY, locZ);
                Location check4 = new Location(curWorld, locX, locY, (locZ - 1));

                Block b1 = check1.getBlock();
                Block b2 = check2.getBlock();
                Block b3 = check3.getBlock();
                Block b4 = check4.getBlock();


                if ((new Location(curWorld, locX, (locY - 1), locZ)).getBlock().getType().equals(Material.AIR)) {
                    SendInfoMessages.sendInfoMessage(p, "NotOnGroundError");
                    return false;
                }

                if (this.cfg.getBoolean("Options.SpawnTorchesAround"))
                {
                    if (!b1.getType().equals(Material.AIR) || !b2.getType().equals(Material.AIR) ||
                            !b3.getType().equals(Material.AIR) || !b4.getType().equals(Material.AIR)) {
                        SendInfoMessages.sendInfoMessage(p, "NoFlatAreaError");
                        return false;
                    }
                }

                boolean dropAtMaxHeight = this.cfg.getBoolean("Options.DropAtMaxHeight");
                int dropHeight = this.cfg.getInt("Options.DropHeight");


                int currentDropHeight = locY + dropHeight;
                if (dropAtMaxHeight) {
                    currentDropHeight = 256;
                }
                int testHeight = locY;

                if (!dropAtMaxHeight)
                {
                    if (locY + dropHeight > 256) {
                        SendInfoMessages.sendInfoMessage(p, "NoSpaceAboveError");
                        return false;
                    }
                }


                while (testHeight < currentDropHeight) {
                    Location testLoc = new Location(curWorld, locX, testHeight, locZ);
                    if (!testLoc.getBlock().getType().equals(Material.AIR)) {
                        SendInfoMessages.sendInfoMessage(p, "NoSpaceAboveError");
                        return false;
                    }
                    testHeight++;
                }


                SendInfoMessages.sendInfoMessage(p, "RequestedDrop", dropName, "");

                if (this.cfg.getBoolean("Options.EnableGlider")) {
                    DropGlider g = new DropGlider(curWorld, locX, (dropAtMaxHeight ? '?' : (locY + dropHeight)), locZ);
                    g.drop(locY);
                }

            } else {

                SendInfoMessages.sendInfoMessage(p, "InsufficientPermissions");
            }
        }
        return false;
    }
}
