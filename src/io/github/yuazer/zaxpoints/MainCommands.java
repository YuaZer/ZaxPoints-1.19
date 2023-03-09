package io.github.yuazer.zaxpoints;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("zaxpoints")) {
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("§b/zaxpoints §a简写-> §b/zaxp(或/zp)");
                sender.sendMessage("§b/zaxpoints reload §a重载配置文件");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload") && sender.isOp()) {
                Main.getInstance().reloadConfig();
                sender.sendMessage(Main.getInstance().getConfig().getString("Message.reload").replace("&", "§"));
                return true;
            }
            if (args[0].equalsIgnoreCase("test")&&sender.isOp()){
                String identifier = args[1];
//                String regex = "zaxpoints_(.*?)_[0-9]+";
                String regex = "%zaxpoints_(.*?)_[0-9]+%";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(identifier);
                if (matcher.find()) {
                    String rankAPI = matcher.group(1); // 获取第一个捕获组的内容
                    if (Main.getInstance().getConfig().getStringList("PointsSetting.rankPAPI").contains("%"+rankAPI+"%")) {
                        int rankNumber = Integer.parseInt(identifier.replace(rankAPI + "_", "").replace("zaxpoints_", "").replace("%",""));
                        Map<UUID, Integer> pointsMap = new HashMap<>();
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            int points = Integer.parseInt(PlaceholderAPI.setPlaceholders(p, "%" + rankAPI + "%"));
                            pointsMap.put(p.getUniqueId(), points);
                        }
                        List<Map.Entry<UUID, Integer>> sortedPlayers = pointsMap.entrySet().stream()
                                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                                .toList();
                        if (rankNumber > 0 && rankNumber <= sortedPlayers.size()) {
                            System.out.println(Bukkit.getPlayer(sortedPlayers.get(rankNumber - 1).getKey()).getName());
                        }else {
                            System.out.println("名次出错");
                        }
                    }else {
                        System.out.println( "配置文件并未有该变量的配置");
                    }
                }else {
                    System.out.println("变量名检测出错");
                }
            }
        }
        return false;
    }
}
