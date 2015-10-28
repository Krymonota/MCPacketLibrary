/**
 * @project: mcpacketlibrary
 * @package: com.craftapi.mcpacketlibrary
 * @file: MCPacketLibrary.java
 * @author: Niklas (Krymonota)
 * @date: 25.10.2015
 */
package com.craftapi.mcpacketlibrary;

import java.io.IOException;
import java.util.logging.Level;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import com.craftapi.mcpacketlibrary.interceptor.MCPacketManager;

public class MCPacketLibrary extends JavaPlugin {

	private static MCPacketLibrary instance;
	private static MCPacketManager packetManager;
	
	@Nonnull
	public static MCPacketLibrary getInstance() {
		return instance;
	}
	
	@Nonnull
	public static MCPacketManager getPacketManager() {
		return packetManager;
	}
	
	@Override
	public void onLoad() {
		instance = this;
	}
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();

		packetManager = new MCPacketManager();

		Bukkit.getServer().getPluginManager().registerEvents(packetManager, this);
		
		 // Start Plugin Metrics (mcstats.org)
		try {
			new Metrics(this).start();
		} catch (IOException e) {
			this.getServer().getLogger().log(Level.SEVERE, "Couldn't submit stats to MCStats.org!", e);
		}
		
		// Start Updater if it's enabled in config
		if (this.getConfig().getBoolean("general.enable-updater")) {}
	}
	
	@Override
	public void onDisable() {
		packetManager = null;
		instance = null;
	}
	
}
