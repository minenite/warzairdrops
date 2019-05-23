package net.minenite.warzairdrops;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import net.minenite.warzairdrops.Main;
import net.minenite.warzairdrops.EnvoysDataManager;
import net.minenite.warzairdrops.RandomDrop;
import net.minenite.warzairdrops.SendInfoMessages;
import net.minenite.warzairdrops.SupplyDropsDataManager;


public class CommandEnvoy
        implements CommandExecutor, Listener
{
    FileConfiguration cfg = Main.getPlugin().getConfig();
    FileConfiguration envoyCfg = EnvoysDataManager.getEnvoysData();
    FileConfiguration dropsCfg = SupplyDropsDataManager.getSupplyDropsData();


    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player)
        { Player p = (Player)sender;
            if (p.hasPermission("supplydrop.admin"))
            { final ArrayList<RandomDrop> randomDrops; final World world; String worldName; final int envoyPos2Z, envoyPos2X, envoyPos1Z, envoyPos1X, envoyDropCount, envoyLength; if (args.length == 0) {
                SendInfoMessages.sendInfoMessage(p, "SpecifyValidEnvoySubcommand");
                return false;
            }

                Location pLoc = p.getLocation();
                int x = (int)pLoc.getX();
                int z = (int)pLoc.getZ();
                String str;
                switch ((str = args[0]).hashCode()) { case -1352294148: if (!str.equals("create")) {
                    break;
                }
                    if (args.length == 1) {
                        SendInfoMessages.sendInfoMessage(p, "SpecifyEnvoyName");
                        return false;
                    }

                    this.envoyCfg.createSection("envoys." + args[1]);
                    this.envoyCfg.set("envoys." + args[1] + ".length", Integer.valueOf(3600));
                    this.envoyCfg.createSection("envoys." + args[1] + ".pos1");
                    this.envoyCfg.createSection("envoys." + args[1] + ".pos2");
                    this.envoyCfg.createSection("envoys." + args[1] + ".drops");
                    this.envoyCfg.set("envoys." + args[1] + ".dropCount", Integer.valueOf(30));
                    EnvoysDataManager.saveEnvoysData();
                    SendInfoMessages.sendInfoMessage(p, "CreatedEnvoy", "", args[1]);
                    return false;case -1335458389: if (!str.equals("delete")) break;  if (args.length == 1) { SendInfoMessages.sendInfoMessage(p, "SpecifyEnvoyName"); return false; }  if (this.envoyCfg.get("envoys." + args[true]) == null) { SendInfoMessages.sendInfoMessage(p, "SpecifyValidEnvoyName"); return false; }  this.envoyCfg.set("envoys." + args[1], null); EnvoysDataManager.saveEnvoysData(); SendInfoMessages.sendInfoMessage(p, "DeletedEnvoy", "", args[1]); return false;case -1148096752: if (!str.equals("adddrop")) break;  if (args.length != 4) { SendInfoMessages.sendInfoMessage(p, "SpecifyAddDropArgs"); return false; }  if (this.envoyCfg.get("envoys." + args[true]) == null) { SendInfoMessages.sendInfoMessage(p, "SpecifyValidEnvoyName"); return false; }  if (this.dropsCfg.getString("drops." + args[2]) == null) { SendInfoMessages.sendInfoMessage(p, "SpecifyValidDropName"); return false; }  try { int chance = Integer.valueOf(args[3]).intValue(); this.envoyCfg.set("envoys." + args[1] + ".drops." + args[2], Integer.valueOf(chance)); EnvoysDataManager.saveEnvoysData(); SendInfoMessages.sendInfoMessage(p, "AddedDropToEnvoy", args[2], args[1]); } catch (Exception e) { SendInfoMessages.sendInfoMessage(p, "SpecifyValidPercentForChance"); return false; }  return false;case -974507682: if (!str.equals("setdropcount")) break;  if (args.length != 3) { SendInfoMessages.sendInfoMessage(p, "SpecifyDropCountArgs"); return false; }  if (this.envoyCfg.get("envoys." + args[true]) == null) { SendInfoMessages.sendInfoMessage(p, "SpecifyValidEnvoyName"); return false; }  try { int count = Integer.valueOf(args[2]).intValue(); this.envoyCfg.set("envoys." + args[1] + ".dropCount", Integer.valueOf(count)); EnvoysDataManager.saveEnvoysData(); SendInfoMessages.sendInfoMessage(p, "SetDropCountForEnvoy", "", args[1]); } catch (Exception e) { SendInfoMessages.sendInfoMessage(p, "SpecifyValidNumberForDropCount"); return false; }  return false;case 3198785: if (!str.equals("help")) break;  p.sendMessage("�8�l-=-=-[�f�l�nSDP�8�l]-=-=-\n �7-> �e/envoy create �oenvoyname�r �7- �eCreate a new envoy\n �7-> �e/envoy pos1 �oenvoyname�r �7- �eSet first envoy boundary position\n �7-> �e/envoy pos2 �oenvoyname�r �7- �eSet second envoy boundary position\n �7-> �e/envoy adddrop �oenvoyname�r �e�odropname�r �e�ochance�r �7- �eAdd a drop with a chance to an envoy\n �7-> �e/envoy removedrop �oenvoyname�r �e�odropname�r �7- �eRemove a drop from an envoy\n �7-> �e/envoy setlength �oenvoyname�r �7- �eSet the length of an envoy\n �7-> �e/envoy setdropcount �oenvoyname�r �7- �eSet the drop count of an envoy\n �7-> �e/envoy start �oenvoyname�r �7- �eStart an envoy\n �7-> �e/envoy delete �oenvoyname�r �7- �eDelete an envoy"); return false;case 3446877: if (!str.equals("pos1")) break;  if (args.length == 1) { SendInfoMessages.sendInfoMessage(p, "SpecifyEnvoyName"); return false; }  if (this.envoyCfg.get("envoys." + args[true]) == null) { SendInfoMessages.sendInfoMessage(p, "SpecifyValidEnvoyName"); return false; }  this.envoyCfg.set("envoys." + args[1] + ".world", pLoc.getWorld().getName()); this.envoyCfg.set("envoys." + args[1] + ".pos1.x", Integer.valueOf(x)); this.envoyCfg.set("envoys." + args[1] + ".pos1.z", Integer.valueOf(z)); EnvoysDataManager.saveEnvoysData(); SendInfoMessages.sendInfoMessage(p, "AddedFirstEnvoyPos", "", args[1]); return false;case 3446878: if (!str.equals("pos2")) break;  if (args.length == 1) { SendInfoMessages.sendInfoMessage(p, "SpecifyEnvoyName"); return false; }  if (this.envoyCfg.get("envoys." + args[true]) == null) { SendInfoMessages.sendInfoMessage(p, "SpecifyValidEnvoyName"); return false; }  this.envoyCfg.set("envoys." + args[1] + ".pos2.x", Integer.valueOf(x)); this.envoyCfg.set("envoys." + args[1] + ".pos2.z", Integer.valueOf(z)); EnvoysDataManager.saveEnvoysData(); SendInfoMessages.sendInfoMessage(p, "AddedSecondEnvoyPos", "", args[1]); return false;case 109757538: if (!str.equals("start")) break;  if (args.length == 1) { SendInfoMessages.sendInfoMessage(p, "SpecifyEnvoyName"); return false; }  if (this.envoyCfg.get("envoys." + args[true]) == null) { SendInfoMessages.sendInfoMessage(p, "SpecifyValidEnvoyName"); return false; }  if (this.envoyCfg.get("envoys." + args[true] + ".pos1.x") == null || this.envoyCfg.get("envoys." + args[true] + ".pos2.x") == null) { SendInfoMessages.sendInfoMessage(p, "SpecifyBothBoundaryPos"); return false; }  if (this.envoyCfg.getConfigurationSection("envoys." + args[1] + ".drops").getKeys(false).isEmpty()) { SendInfoMessages.sendInfoMessage(p, "SpecifyEnvoyDrops"); return false; }  envoyLength = this.envoyCfg.getInt("envoys." + args[1] + ".length"); envoyDropCount = this.envoyCfg.getInt("envoys." + args[1] + ".dropCount"); envoyPos1X = this.envoyCfg.getInt("envoys." + args[1] + ".pos1.x"); envoyPos1Z = this.envoyCfg.getInt("envoys." + args[1] + ".pos1.z"); envoyPos2X = this.envoyCfg.getInt("envoys." + args[1] + ".pos2.x"); envoyPos2Z = this.envoyCfg.getInt("envoys." + args[1] + ".pos2.z"); worldName = this.envoyCfg.getString("envoys." + args[1] + ".world"); world = Bukkit.getWorld(worldName); randomDrops = new ArrayList<RandomDrop>(); for (String key : this.envoyCfg.getConfigurationSection("envoys." + args[1] + ".drops").getKeys(false)) { int val = this.envoyCfg.getInt("envoys." + args[1] + ".drops." + key); randomDrops.add(new RandomDrop(key, val)); }  (new BukkitRunnable() { int droppedCount = 0; public void run() { this.droppedCount++; Random randPos = new Random(); int totalSum = 0; for (RandomDrop drop : randomDrops) totalSum += drop.dropProbability;  int index = randPos.nextInt(totalSum); int sum = 0; int i = 0; while (sum < index) sum += ((RandomDrop)this.val$randomDrops.get(i++)).dropProbability;  String selectedDrop = ((RandomDrop)this.val$randomDrops.get(Math.max(0, i - 1))).dropName; int rndLocX = CommandEnvoy.this.getRandomBetween(envoyPos1X, envoyPos2X); int rndLocZ = CommandEnvoy.this.getRandomBetween(envoyPos1Z, envoyPos2Z); int curHeight = 256; boolean isChecking = true; while (isChecking) { curHeight--; Location testLoc = new Location(world, rndLocX, curHeight, rndLocZ); Material testBlock = testLoc.getBlock().getType(); if (!testBlock.equals(Material.AIR)) isChecking = false;  }  if (this.droppedCount == envoyDropCount) cancel();  } }).runTaskTimer(Main.getPlugin(), 0L, (envoyLength / envoyDropCount)); SendInfoMessages.sendInfoMessage(p, "StartedEnvoy", "", args[1]); return false;case 1099056499: if (!str.equals("removedrop")) break;  if (args.length != 3) { SendInfoMessages.sendInfoMessage(p, "SpecifyRemoveDropArgs"); return false; }  if (this.envoyCfg.get("envoys." + args[true]) == null) { SendInfoMessages.sendInfoMessage(p, "SpecifyValidEnvoyName"); return false; }  if (this.dropsCfg.getString("drops." + args[2]) == null) { SendInfoMessages.sendInfoMessage(p, "SpecifyValidDropName"); return false; }  this.envoyCfg.set("envoys." + args[1] + ".drops." + args[2], null); EnvoysDataManager.saveEnvoysData(); EnvoysDataManager.saveEnvoysData(); SendInfoMessages.sendInfoMessage(p, "RemovedDropFromEnvoy", args[2], args[1]); return false;case 1291167176: if (!str.equals("setlength")) break;  if (args.length != 3) { SendInfoMessages.sendInfoMessage(p, "SpecifySetLengthArgs"); return false; }  if (this.envoyCfg.get("envoys." + args[true]) == null) { SendInfoMessages.sendInfoMessage(p, "SpecifyValidEnvoyName"); return false; }  try { int length = Integer.valueOf(args[2]).intValue(); this.envoyCfg.set("envoys." + args[1] + ".length", Integer.valueOf(length * 60 * 20)); EnvoysDataManager.saveEnvoysData(); SendInfoMessages.sendInfoMessage(p, "SetLengthOfEnvoy", "", args[1]); } catch (Exception e) { SendInfoMessages.sendInfoMessage(p, "SpecifyValidNumberForLength"); return false; }  return false; }  SendInfoMessages.sendInfoMessage(p, "SpecifyValidEnvoySubcommand"); } else { SendInfoMessages.sendInfoMessage(p, "InsufficientPermissions"); }  }  return false;
    }

    public int getRandomBetween(int min, int max) {
        Random r = new Random();
        int mn = min;
        int mx = max;
        if (max < min) {
            mn = max;
            mx = min;
        }
        return r.nextInt(mx + 1 - mn) + mn;
    }
}
