package io.github.yuazer.zaxpoints;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RankPoints extends PlaceholderExpansion {
    private final ExecutorService executorService;

    public RankPoints() {
        // 创建一个线程数为4的固定大小线程池
        executorService = Executors.newFixedThreadPool(4);
    }

    @Override
    public String getIdentifier() {
        return "zaxpoints";
    }

    @Override
    public String getAuthor() {
        return "Z菌";
    }

    @Override
    public String getVersion() {
        return Main.getInstance().getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        String regex = "(.*?)_[0-9]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(identifier);
        if (matcher.find()) {
            String rankAPI = matcher.group(1); // 获取第一个捕获组的内容
            if (Main.getInstance().getConfig().getStringList("PointsSetting.rankPAPI").contains("%" + rankAPI + "%")) {
                int rankNumber = Integer.parseInt(identifier.replace(rankAPI + "_", "").replace("%", ""));
                CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    Map<UUID, Integer> pointsMap = new HashMap<>();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        int points = Integer.parseInt(PlaceholderAPI.setPlaceholders(p, "%" + rankAPI + "%"));
                        pointsMap.put(p.getUniqueId(), points);
                    }
                    List<Map.Entry<UUID, Integer>> sortedPlayers = pointsMap.entrySet().stream()
                            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                            .toList();
                    if (rankNumber > 0 && rankNumber <= sortedPlayers.size()) {
                        return (Bukkit.getPlayer(sortedPlayers.get(rankNumber - 1).getKey()).getName());
                    } else {
                        return (Main.getInstance().getConfig().getString("Message.rankNullError").replace("&", "§"));
                    }
                }, executorService);
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            } else {
                return ("配置文件并未有该变量的配置");
            }
        } else {
            return ("变量名检测出错");
        }
        return "error";
    }
}
