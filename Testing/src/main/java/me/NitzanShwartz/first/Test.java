package me.NitzanShwartz.first;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class Test extends JavaPlugin {

    public static Location location;
    public static String spot;
    public static Player player;
    public static FileConfiguration config;

    @Override
    public void onEnable(){
        Bukkit.getLogger().info("Plugin loaded.");
        Bukkit.getServer().getPluginManager().registerEvents(new EventsHandler(), this);
        config = this.getConfig();
    }

    @Override
    public void onDisable(){
        Bukkit.getLogger().info("Plugin shut down.");
        config.options().copyDefaults(true);
        saveConfig();

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player) {
            player = (Player) sender;
            if (label.equals("setwarp")) {
                if(!config.getBoolean(args[0])) {
                    location = player.getLocation();
                    spot = args[0];

                    config.set(spot, true);
                    config.set(spot + "x", location.getX());
                    config.set(spot + "y", location.getY());
                    config.set(spot + "z", location.getZ());
                    config.options().copyDefaults(true);
                    saveConfig();

                    player.sendMessage(ChatColor.GREEN + "Warp " + args[0] + " had been set successfully.");

                    return true;
                }

                else {

                    location = player.getLocation();

                    config.set(args[0] + "x", location.getX());
                    config.set(args[0] + "y", location.getY());
                    config.set(args[0] + "z", location.getZ());
                    config.options().copyDefaults(true);
                    saveConfig();

                    player.sendMessage(ChatColor.GREEN + "Warp place changed.");

                }
            }

            else if(label.equals("tpwarp")){

                if(config.getBoolean(args[0])){
                    player.sendMessage(ChatColor.GREEN + "You are being TP'd to the " + args[0] + " warp.");
                    Location location_from_config = new Location(Bukkit.getWorld("world"), config.getDouble(args[0]+ "x"), config.getDouble(args[0]+ "y"), config.getDouble(args[0]+ "z"));
                    player.teleport((location_from_config));
                    return true;
                }

                player.sendMessage(ChatColor.RED + "This warp does not exist!");
            }

            else if(label.equals("delwarp"))
            {
                if(config.getBoolean(args[0]))
                {
                    player.sendMessage(ChatColor.GREEN + "The " + args[0] +" warp had been deleted.");
                    config.set(args[0], null);
                    config.set(args[0] + "x", null);
                    config.set(args[0] + "y", null);
                    config.set(args[0] + "z", null);
                    config.options().copyDefaults(true);
                    saveConfig();
                    return true;
                }

                player.sendMessage(ChatColor.RED + "This warp does not exist!");

            }

            else if(label.equals("warps")){
                String allWarps = ChatColor.LIGHT_PURPLE + "Available warps are: ";
                for (String key : config.getKeys(false))
                {
                     if(config.getBoolean(key)){
                        allWarps += key +" ";
                     }
                }

                if(allWarps.equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Available warps are: ")){
                    player.sendMessage(ChatColor.RED + "There are no warps");

                    return true;
                }

                else player.sendMessage(allWarps);
            }
        }
        return true;
    }
}
