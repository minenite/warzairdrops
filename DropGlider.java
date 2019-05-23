package net.minenite.warzairdrops;

import java.util.HashMap;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import net.minenite.warzairdrops.Main;



public class DropGlider
{
    private HashMap<String, ArmorStand> parts = new HashMap();

    public DropGlider(World world, double x, double y, double z) {
        Location loc = new Location(world, x + 0.5D, y, z + 0.5D);

        ArmorStand leftSide = makeNewArmorStand(loc.getWorld(), loc.getX() - 0.32D, loc.getY() + 0.93D, loc.getZ());
        leftSide.setHeadPose(new EulerAngle(0.0D, Math.toRadians(270.0D), Math.toRadians(251.0D)));
        leftSide.setHelmet(new ItemStack(Material.BANNER));

        ArmorStand middle = makeNewArmorStand(loc.getWorld(), loc.getX() + 1.345D, loc.getY() + 0.76D, loc.getZ());
        middle.setHeadPose(new EulerAngle(0.0D, Math.toRadians(270.0D), Math.toRadians(270.0D)));
        middle.setHelmet(new ItemStack(Material.BANNER));

        ArmorStand rightSide = makeNewArmorStand(loc.getWorld(), loc.getX() + 0.32D, loc.getY() + 0.93D, loc.getZ());
        rightSide.setHeadPose(new EulerAngle(0.0D, Math.toRadians(90.0D), Math.toRadians(109.0D)));
        rightSide.setHelmet(new ItemStack(Material.BANNER));

        ArmorStand leftStick = makeNewArmorStand(loc.getWorld(), loc.getX() + 0.62D, loc.getY() - 0.55D,
                loc.getZ() + 0.28D);
        leftStick.setHelmet(new ItemStack(Material.STICK));

        ArmorStand rightStick = makeNewArmorStand(loc.getWorld(), loc.getX() - 0.62D, loc.getY() + 0.92D,
                loc.getZ() - 0.28D);
        rightStick.setHeadPose(new EulerAngle(0.0D, Math.toRadians(180.0D), Math.toRadians(180.0D)));
        rightStick.setHelmet(new ItemStack(Material.STICK));

        this.parts.put("leftSide", leftSide);
        this.parts.put("middle", middle);
        this.parts.put("rightSide", rightSide);
        this.parts.put("leftStick", leftStick);
        this.parts.put("rightStick", rightStick);
    }


    public void drop(final double finalY) { (new BukkitRunnable() {
        public void run() {
            if (((ArmorStand)DropGlider.access$0(DropGlider.this).get("middle")).getLocation().getY() > finalY + 1.0D) {
                for (String part : DropGlider.access$0(DropGlider.this).keySet()) {
                    ((ArmorStand)DropGlider.access$0(DropGlider.this).get(part)).teleport(((ArmorStand)DropGlider.access$0(DropGlider.this).get(part)).getLocation().subtract(0.0D, 1.0D, 0.0D));
                }
            } else {
                for (String part : DropGlider.access$0(DropGlider.this).keySet()) {
                    ((ArmorStand)DropGlider.access$0(DropGlider.this).get(part)).remove();
                }
                cancel();
            }
        }
    }).runTaskTimer(Main.getPlugin(), 0L, 2L); }


    private static ArmorStand makeNewArmorStand(World world, double x, double y, double z) {
        Location asLoc = new Location(world, x, y, z);
        ArmorStand as = (ArmorStand)asLoc.getWorld().spawn(asLoc, ArmorStand.class);
        as.setBasePlate(false);
        as.setArms(false);
        as.setVisible(false);
        as.setInvulnerable(true);
        as.setCanPickupItems(false);
        as.setGravity(false);
        as.setSmall(false);
        return as;
    }
}
