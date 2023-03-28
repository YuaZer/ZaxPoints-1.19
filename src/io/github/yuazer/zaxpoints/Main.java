package io.github.yuazer.zaxpoints;

import io.github.yuazer.zaxpoints.Listener.PlayerEvents;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        File file = new File("plugins/ZaxPoints/OfflineSave");
        if (!file.exists()) {
            file.mkdir();
        }
        Bukkit.getPluginManager().registerEvents(new PlayerEvents(), this);
        logLoaded(this);
        saveDefaultConfig();
    }

    public void onDisable() {
        logDisable(this);
        RankPoints rank = new RankPoints();
        rank.unregister();
    }

    public static void logLoaded(JavaPlugin plugin) {
        Bukkit.getLogger().info(String.format("§e[§b%s§e] §f已加载", plugin.getName()));
        Bukkit.getLogger().info("§b作者:§eZ菌");
        Bukkit.getLogger().info("§b版本:§e" + plugin.getDescription().getVersion());
        RankPoints rank = new RankPoints();
        RankPointsReverse rankR = new RankPointsReverse();
        if (!rank.isRegistered()) {
            rank.register();
        }
        if (!rankR.isRegistered()) {
            rankR.register();
        }
        Bukkit.getPluginCommand("zaxpoints").setExecutor(new MainCommands());
    }

    public static void logDisable(JavaPlugin plugin) {
        Bukkit.getLogger().info(String.format("§e[§b%s§e] §c已卸载", plugin.getName()));
    }
}
