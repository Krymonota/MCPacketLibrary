/**
 * @project: mcpacketlibrary
 * @package: io.github.krymonota.mcpacketlibrary
 * @file: MCPacketLibrary.java
 * @author: Niklas (Krymonota)
 * @date: 25.10.2015
 */
package io.github.krymonota.mcpacketlibrary;

import io.github.krymonota.mcpacketlibrary.interceptor.MCPacketManager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MCPacketLibrary extends JavaPlugin {

	private static MCPacketLibrary instance;
	private static MCPacketManager packetManager;

	public static MCPacketLibrary getInstance() {
		return instance;
	}

	public static MCPacketManager getPacketManager() {
		return packetManager;
	}
	
	@Override
	public void onLoad() {
		instance = this;
		packetManager = new MCPacketManager();
	}
	
	@Override
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(packetManager, this);
	}
	
	@Override
	public void onDisable() {
		packetManager = null;
		instance = null;
	}
	
}
