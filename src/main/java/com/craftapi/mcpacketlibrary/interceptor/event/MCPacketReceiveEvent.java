package com.craftapi.mcpacketlibrary.interceptor.event;

import net.minecraft.server.v1_8_R3.Packet;

import org.bukkit.entity.Player;

public class MCPacketReceiveEvent extends MCPacketEvent {

	public MCPacketReceiveEvent(Player player, Packet<?> packet) {
		super(player, packet);
	}

}