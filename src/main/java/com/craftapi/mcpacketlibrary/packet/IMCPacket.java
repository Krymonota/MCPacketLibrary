package com.craftapi.mcpacketlibrary.packet;

import java.util.List;

import net.minecraft.server.v1_8_R3.Packet;

public interface IMCPacket {

	/**
	 * @return The packets to send to the player.
	 */
	public Packet<?> getPacket();

	/**
	 * @return A list of the protocol versions this packets is acceptable for.
	 * @see http://wiki.vg/Protocol_version_numbers
	 */
	public List<Integer> getProtocol();
	
}
