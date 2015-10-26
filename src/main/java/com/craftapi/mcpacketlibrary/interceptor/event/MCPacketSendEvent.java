/**
 * @project: mcpacketlibrary
 * @package: com.craftapi.mcpacketlibrary.interceptor.event
 * @file: MCPacketSendEvent.java
 * @author: Niklas (Krymonota)
 * @date: 25.10.2015
 */
package com.craftapi.mcpacketlibrary.interceptor.event;

import net.minecraft.server.v1_8_R3.Packet;

import org.bukkit.entity.Player;

public class MCPacketSendEvent extends MCPacketEvent {

	public MCPacketSendEvent(Player player, Packet<?> packet) {
		super(player, packet);
	}

}