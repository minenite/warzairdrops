package net.minenite.warzairdrops;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import net.minenite.warzairdrops.CommandDeleteSupplyDrop;
import net.minenite.warzairdrops.CommandEditSupplyDrop;
import net.minenite.warzairdrops.CommandEnvoy;
import net.minenite.warzairdrops.CommandSupplyDrop;
import net.minenite.warzairdrops.EnvoysDataManager;
import net.minenite.warzairdrops.SupplyDropsDataManager;


public class Main
        extends JavaPlugin
{
    private static Plugin plugin;

    public static Plugin getPlugin() { return plugin; }



    public void onEnable() {
        plugin = this;
        Bukkit.getConsoleSender().sendMessage("Enabled Supply Drop Plus");


        saveDefaultConfig();

        SupplyDropsDataManager.saveDefaultConfig();

        EnvoysDataManager.saveDefaultConfig();


        Bukkit.getServer().getPluginManager().registerEvents(new CommandEditSupplyDrop(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CommandEnvoy(), this);


        getCommand("supplydrop").setExecutor(new CommandSupplyDrop());
        getCommand("editsupplydrop").setExecutor(new CommandEditSupplyDrop());
        getCommand("deletesupplydrop").setExecutor(new CommandDeleteSupplyDrop());
        getCommand("envoy").setExecutor(new CommandEnvoy());
    }


    public void onDisable() {
        plugin = null;
        Bukkit.getConsoleSender().sendMessage("Disabled Supply Drop Plus");
    }
}
