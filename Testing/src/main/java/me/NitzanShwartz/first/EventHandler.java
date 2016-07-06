package me.NitzanShwartz.first;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;



class EventsHandler implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getState() instanceof Sign) {

                Sign sign = (Sign) event.getClickedBlock().getState();
                String[] lines = sign.getLines();
            if(sign.getLine(0).equalsIgnoreCase(org.bukkit.ChatColor.AQUA + "[Warp]")) {
                for (String line : lines) {
                    if (Test.config.getBoolean(line)) {
                        String warp = line;
                        Test.player.sendMessage(ChatColor.GREEN + "You are being TP'd to the " + warp + " warp.");
                        Location location_from_config = new Location(Bukkit.getWorld("world"),
                                Test.config.getDouble(warp+ "x"), Test.config.getDouble(warp+ "y"),
                                    Test.config.getDouble(warp+ "z"));
                        event.getPlayer().teleport(location_from_config);

                    }
                }
            }

            }
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event){
        if(event.getLine(0).equalsIgnoreCase("[Warp]")){
            event.setLine(0, ChatColor.AQUA + "[Warp]");
        }
    }
}

