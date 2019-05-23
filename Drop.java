package net.minenite.warzairdrops;

import java.io.IOException;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import net.minenite.warzairdrops.Main;


public class Drop
{
    FileConfiguration cfg = Main.getPlugin().getConfig();


    public Drop(final String dropName, final World curWorld, final int locX, final int locY, final int locZ, int height, final boolean isEnvoy) { (new BukkitRunnable(height) {
        int currentDropHeight;

        public void run() {
            Location prevLoc = new Location(curWorld, locX, (this.currentDropHeight + 1), locZ);
            Location dropLoc = new Location(curWorld, locX, this.currentDropHeight, locZ);
            if (this.currentDropHeight > locY) {
                prevLoc.getBlock().setType(Material.AIR);
                dropLoc.getBlock().setType(Material.CHEST);
                this.currentDropHeight--;
            } else {
                prevLoc.getBlock().setType(Material.AIR);
                dropLoc.getBlock().setType(Material.CHEST);

                if (Drop.this.cfg.getBoolean("Options.SpawnTorchesAround") && !isEnvoy) {

                    Drop.this.setBlock(curWorld, locX + 1, locY, locZ, Material.REDSTONE_TORCH_ON);
                    Drop.this.setBlock(curWorld, locX, locY, locZ + 1, Material.REDSTONE_TORCH_ON);
                    Drop.this.setBlock(curWorld, locX - 1, locY, locZ, Material.REDSTONE_TORCH_ON);
                    Drop.this.setBlock(curWorld, locX, locY, locZ - 1, Material.REDSTONE_TORCH_ON);
                }

// error city
                Chest chest = (Chest)dropLoc.getBlock().getState();
                Inventory inv = chest.getInventory();
                chest.setDisplayName(ChatColor.translateAlternateColorCodes('&', Drop.this.cfg.getString("Options.SupplyDropName").replaceAll("%dropName%", dropName)));
                chest.update();
                try {
                    Inventory loadedDropInv =
                            InventorySerializer.fromBase64(SupplyDropsDataManager.getSupplyDropsData().getString("drops." + dropName));
                    ItemStack[] dropInvItems = loadedDropInv.getContents();
                    for (int i = 0; i < dropInvItems.length; i++) {
                        if (dropInvItems[i] != null) {
                            inv.setItem(i, dropInvItems[i]);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }



                dropLoc.getWorld().playSound(dropLoc, Sound.AMBIENCE_CAVE, 1.0F, -10.0F);


                Firework f = (Firework)dropLoc.getWorld()
                        .spawn(new Location(curWorld, locX + 0.5D, locY, locZ + 0.5D), Firework.class);
                FireworkMeta meta = f.getFireworkMeta();
                meta.addEffect(FireworkEffect.builder().flicker(false).trail(true).with(FireworkEffect.Type.valueOf(Drop.this.cfg.getString("Options.SupplyDropFireworkType")))
                        .withColor(Color.RED).withColor(Color.WHITE).withFade(Color.GRAY).build());
                f.setFireworkMeta(meta);
                f.eject();

                cancel();
            }

        }
    }).runTaskTimer(Main.getPlugin(), isEnvoy ? 0L : 3L, 2L); }





    public void setBlock(World world, int x, int y, int z, Material matName) { (new Location(world, x, y, z)).getBlock().setType(matName); }
}
