package com.cainkilgore.timerotate;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class TimeRotate extends JavaPlugin implements Listener {
	
	// Variables used in-code
	public static boolean shouldDo = false;
	String whichWorld = "";
	
	public void onEnable() {
		// Event Registration
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		
		// Crafting Registration
		ItemStack clock = new ItemStack(Material.WATCH, 1);
		ItemMeta clockmeta = clock.getItemMeta();
		clockmeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Time Rotator");
		clock.setItemMeta(clockmeta);
		ShapelessRecipe time = new ShapelessRecipe(clock);
		time.addIngredient(Material.EYE_OF_ENDER);
		time.addIngredient(Material.WATCH);
		Bukkit.getServer().addRecipe(time);
		
		// Scheduler Start
		Bukkit.getServer().getScheduler().runTaskTimer(this, new Runnable() {
			public void run() {
				if(!shouldDo) return;
				Bukkit.getServer().getWorld(whichWorld).setTime(Bukkit.getServer().getWorld(whichWorld).getTime() + 120L);
			}
		}, 1L, 1L);
	}
	
	public void onDisable() { } // Compact ?
	
	// Registering Player Interaction Event
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		// Item / Permission Checks
		if(!e.getPlayer().isOp()) return;
		if(!(e.getPlayer().getItemInHand().getType() == Material.WATCH)) return;
		if(!e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.LIGHT_PURPLE + "Time Rotator")) return;
		
		// Toggle Code
		if(!shouldDo) {
			whichWorld = e.getPlayer().getWorld().getName();
			shouldDo = true;
			e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Time Rotate> " + ChatColor.WHITE + "The time will now begin to rotate.");
			return;
		}
		
		e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + "Time Rotate> " + ChatColor.WHITE + "The time has stopped rotating.");
		shouldDo = false;
	}

}