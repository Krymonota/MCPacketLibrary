/**
 * @project: mcpacketlibrary
 * @package: com.craftapi.mcpacketlibrary.interceptor.event
 * @file: MCPacketEvent.java
 * @author: Niklas (Krymonota)
 * @date: 25.10.2015
 */
package com.craftapi.mcpacketlibrary.interceptor.event;

import net.minecraft.server.v1_8_R3.Packet;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public abstract class MCPacketEvent implements Cancellable {

	protected Player player;
	protected Packet<?> packet;
	private boolean cancelled = false;

	public MCPacketEvent(Player player, Packet<?> packet) {
		this.player = player;
		this.packet = packet;
	}

	public Player getPlayer() {
		return this.player;
	}
	
	public Packet<?> getPacket() {
		return this.packet;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancelled = cancel;
	}

	@Override
	public boolean isCancelled() {
		return this.cancelled;
	}

}
