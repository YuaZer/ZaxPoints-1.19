package io.github.yuazer.zaxpoints.Listener;

import io.github.yuazer.zaxpoints.DataUtils.OfflineUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;

public class PlayerEvents implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) throws IOException {
        Player player = e.getPlayer();
        File file = new File("plugins/ZaxPoints/OfflineSave/" + player.getName() + ".yml");
        if (!file.exists()) {
            file.createNewFile();
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e) throws IOException {
        Player player = e.getPlayer();
        OfflineUtils.offlineDataSave(player);
    }
}
