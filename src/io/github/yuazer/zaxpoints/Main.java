package io.github.yuazer.zaxpoints;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public static void logLoaded(JavaPlugin plugin) {
        Bukkit.getLogger().info(String.format("§e[§b%s§e] §f已加载", plugin.getName()));
        Bukkit.getLogger().info("§b作者:§eZ菌");
        Bukkit.getLogger().info("§b版本:§e" + plugin.getDescription().getVersion());
        RankPoints rank = new RankPoints();
        if (!rank.isRegistered()){
            rank.register();
        }
        Bukkit.getPluginCommand("zaxpoints").setExecutor(new MainCommands());
    }
    public static void logDisable(JavaPlugin plugin) {
        Bukkit.getLogger().info(String.format("§e[§b%s§e] §c已卸载", plugin.getName()));
    }
    public void onEnable(){
        instance = this;
        logLoaded(this);
        saveDefaultConfig();
    }
    public void onDisable(){
        logDisable(this);
        RankPoints rank = new RankPoints();
        rank.unregister();
    }
}
