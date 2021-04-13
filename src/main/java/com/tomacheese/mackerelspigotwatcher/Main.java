package com.tomacheese.mackerelspigotwatcher;

import com.tomacheese.mackerelspigotwatcher.task.Task_SendMetrics;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import com.tomacheese.mackerelspigotwatcher.lib.MackerelAPI;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public final class Main extends JavaPlugin {

    private static JavaPlugin JavaPlugin;
    private static MackerelAPI MackerelAPI;

    /**
     * called when enable plugin
     * @author mine_book000
     * @since 2020/06/04
     */
    @Override
    public void onEnable() {
        JavaPlugin = this;
        getLogger().info("MackerelSpigotWatcher created by Tomachi (mine_book000)");
        getLogger().info("ProjectPage: https://github.com/jaoafa/MackerelSpigotWatcher");

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        FileConfiguration conf = getConfig();

        String mackerelAPIKey;
        String mackerelHostId;

        // ---------- Get API KEY ---------- //

        if (conf.contains("apikey") && conf.isString("apikey") && !Objects.requireNonNull(conf.getString("apikey")).equals("null")) {
            mackerelAPIKey = conf.getString("apikey");
        } else {
            getLogger().warning("apikey is not defined in the config, or an invalid value is set.");
            getLogger().warning("Disable the plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        // ---------- Get Host Id ---------- //

        File mackerelHostIdFile = new File("/var/lib/mackerel-agent/id");
        if (!mackerelHostIdFile.exists() && new File("C:\\Program Files (x86)\\Mackerel\\mackerel-agent\\id").exists()) {
            mackerelHostIdFile = new File("C:\\Program Files (x86)\\Mackerel\\mackerel-agent\\id");
        }
        if (!mackerelHostIdFile.exists() && new File("C:\\Program Files\\Mackerel\\mackerel-agent\\id").exists()) {
            mackerelHostIdFile = new File("C:\\Program Files\\Mackerel\\mackerel-agent\\id");
        }
        if (!mackerelHostIdFile.exists()) {
            // not found
            getLogger().warning("Could not get Mackerel HostId. The id file cannot be found.");
            getLogger().warning("Disable the plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (!mackerelHostIdFile.canRead()) {
            // cant read
            getLogger().warning("Could not get Mackerel HostId. You do not have read permission for the id file.");
            getLogger().warning("Disable the plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        try {
            mackerelHostId = String.join("\n", Files.readAllLines(mackerelHostIdFile.toPath()));
        } catch (IOException e) {
            getLogger().warning("Could not get Mackerel HostId.");
            getLogger().warning("Disable the plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        MackerelAPI = new MackerelAPI(mackerelAPIKey, mackerelHostId);
        try {
            JSONObject json = MackerelAPI.getHostInfomation();
            if (json == null) {
                getLogger().warning("Could not get Mackerel Host Infomation.");
                getLogger().warning("Disable the plugin.");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
            JSONObject hostInfo = json.getJSONObject("host");
            String printName;
            if (hostInfo.has("displayName") && !hostInfo.isNull("displayName")) {
                printName = hostInfo.getString("displayName") + " (" + hostInfo.getString("name") + ")";
            } else {
                printName = hostInfo.getString("name");
            }

            getLogger().info("Host: " + printName);

            if (hostInfo.getBoolean("isRetired")) {
                // retired
                getLogger().warning("The specified host is retired.");
                getLogger().warning("Disable the plugin.");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
            getLogger().warning("Could not get Mackerel Host Infomation.");
            getLogger().warning("Disable the plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        new Task_SendMetrics().runTaskTimer(this, 0L, 1200L); // every 1 min
    }

    public static JavaPlugin getJavaPlugin() {
        return JavaPlugin;
    }

    public static MackerelAPI getMackerelAPI() {
        return MackerelAPI;
    }
}