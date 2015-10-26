/**
 * @project: mcpacketlibrary
 * @package: com.craftapi.mcpacketlibrary
 * @file: MCPacketLibrary.java
 * @author: Niklas (Krymonota)
 * @date: 25.10.2015
 */
package com.craftapi.mcpacketlibrary;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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
	public void onEnable() {
		instance = this;
		packetManager = new MCPacketManager();

		Bukkit.getServer().getPluginManager().registerEvents(packetManager, this);
	}
	
	@Override
	public void onDisable() {
		packetManager = null;
		instance = null;
	}
	
}
