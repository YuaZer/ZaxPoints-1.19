package io.github.yuazer.zaxpoints.DataUtils;

import io.github.yuazer.zaxpoints.Main;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class OfflineUtils {
    public static void offlineDataSave(Player player) throws IOException {
        File file = new File("plugins/ZaxPoints/OfflineSave/" + player.getName() + ".yml");
        if (!file.exists()) {
            file.createNewFile();
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(file);
        Main.getInstance().getConfig().getStringList("OfflineData").forEach((i) -> {
            conf.set(i, PlaceholderAPI.setPlaceholders(player, i));
        });
        conf.save(file);
    }
}
