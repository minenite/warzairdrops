package net.minenite.warzairdrops;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import net.minenite.warzairdrops.Main;







public class SupplyDropsDataManager
{
    private static File customConfigFile;
    private static FileConfiguration customConfig;

    public static FileConfiguration getSupplyDropsData() { return customConfig; }


    public static void saveDefaultConfig() {
        customConfigFile = new File(Main.getPlugin().getDataFolder(), "supplydrops.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            Main.getPlugin().saveResource("supplydrops.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void saveSupplyDropsData() {
        try {
            getSupplyDropsData().save(customConfigFile);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
