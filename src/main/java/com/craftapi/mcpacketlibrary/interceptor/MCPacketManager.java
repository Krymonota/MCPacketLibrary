/**
 * @project: mcpacketlibrary
 * @package: com.craftapi.mcpacketlibrary.interceptor
 * @file: MCPacketManager.java
 * @author: Niklas (Krymonota)
 * @date: 25.10.2015
 */
package com.craftapi.mcpacketlibrary.interceptor;

import io.netty.channel.Channel;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.ServerConnection;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;

import com.craftapi.mcpacketlibrary.MCPacketLibrary;
import com.craftapi.mcpacketlibrary.interceptor.event.MCPacketReceiveEvent;
import com.craftapi.mcpacketlibrary.interceptor.event.MCPacketSendEvent;
import com.craftapi.mcpacketlibrary.util.ReflectionUtil;

public class MCPacketManager implements Listener {

	private final String CHANNEL = MCPacketLibrary.getInstance().getName();
	private List<MCPacketListener> listeners = new ArrayList<MCPacketListener>();

	/**
	 * Register all listeners and put them into a list, so they all get called when the event is fired.
	 * 
	 * @param listener The listener to listen to.
	 */
	public void registerListener(MCPacketListener listener) {
		if (!this.listeners.contains(listener))
			this.listeners.add(listener);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerLogin(PlayerLoginEvent event) {
		if (event.getResult() != Result.ALLOWED)
			return;

		this.inject(event.getPlayer(), event.getAddress());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		if (!this.hasInjected(event.getPlayer()))
			this.inject(event.getPlayer());
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		if (this.hasInjected(event.getPlayer()))
			this.uninject(event.getPlayer());
	}

	/**
	 * @param player The player to receive the packet from.
	 * @param packet The packet to receive.
	 * @return True if the packet can income and the event has not been cancelled.
	 */
	public boolean canIncome(Player player, Packet<?> packet) {
		MCPacketReceiveEvent event = new MCPacketReceiveEvent(player, packet);
		this.listeners.forEach(listener -> listener.onPacketReceive(event));

		return !event.isCancelled();
	}

	/**
	 * @param player The player to send the packet to.
	 * @param packet The packet to send.
	 * @return True if the packet will get sent and the event has not been cancelled.
	 */
	public boolean willSend(Player player, Packet<?> packet) {
		MCPacketSendEvent event = new MCPacketSendEvent(player, packet);
		this.listeners.forEach(listener -> listener.onPacketSend(event));

		return !event.isCancelled();
	}
	
	/**
	 * Inject the channel to a player's netty channel.
	 * 
	 * @param player The player to inject the channel to.
	 */
	public void inject(Player player) {
		this.getNettyChannel(player).pipeline().addBefore("packet_handler", CHANNEL, new MCPacketInterceptor(player));
	}

	/**
	 * Inject the channel to a player's netty channel.
	 * 
	 * @param player The player to inject the channel to.
	 */
	@SuppressWarnings("unchecked")
	public void inject(Player player, InetAddress address) {
		ServerConnection serverConnection = ((CraftServer) Bukkit.getServer()).getServer().aq();
		List<NetworkManager> networkManagers = (List<NetworkManager>) ReflectionUtil.get("h", serverConnection);
			
		for (NetworkManager manager : networkManagers) {
			if (((InetSocketAddress) manager.getSocketAddress()).getAddress() == address) {
				try {
					Field channelField = manager.getClass().getDeclaredField("channel");
					channelField.setAccessible(true);
					Channel channel = (Channel) channelField.get(manager);
					
					if (!hasInjected(channel))
						channel.pipeline().addBefore("packet_handler", CHANNEL, new MCPacketInterceptor(player));
				} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Uninject the channel from a player's netty channel.
	 * 
	 * @param player The player to uninject the channel from.
	 */
	public void uninject(Player player) {
		Channel channel = getNettyChannel(player);
		channel.eventLoop().submit(() -> {
			channel.pipeline().remove(this.CHANNEL);

			return null;
		});
	}
	
	/**
	 * 
	 * @param player The player to check for.
	 * @return True if the player's channel has been already injected.
	 */
	public boolean hasInjected(Player player) {
		return this.hasInjected(getNettyChannel(player));
	}
	
	/**
	 * 
	 * @param channel The channel to check for.
	 * @return True if the player's channel has been already injected.
	 */
	public boolean hasInjected(Channel channel) {
		return channel.pipeline().get(CHANNEL) != null;
	}

	/**
	 * @param player The player to get the netty channel from.
	 * @return Channel The Netty Channel of the player.
	 */
	public Channel getNettyChannel(Player player) {
		EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
		NetworkManager networkManager = entityPlayer.playerConnection.networkManager;

		try {
			Field channelField = networkManager.getClass().getDeclaredField("channel");
			channelField.setAccessible(true);
			Channel channel = (Channel) channelField.get(networkManager);

			return channel;
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		return null;
	}

}
