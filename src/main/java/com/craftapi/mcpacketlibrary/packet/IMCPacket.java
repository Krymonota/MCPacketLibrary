/**
 * @project: mcpacketlibrary
 * @package: com.craftapi.mcpacketlibrary.packet
 * @file: IMCPacket.java
 * @author: Niklas (Krymonota)
 * @date: 25.10.2015
 */
package com.craftapi.mcpacketlibrary.packet;

import java.util.List;

import net.minecraft.server.v1_8_R3.Packet;

public interface IMCPacket {

	/**
	 * @return Packet<?> The packet to send to the player.
	 */
	public Packet<?> getPacket();

	/**
	 * @return List<Integer> A list of the protocol versions this packets is acceptable for.
	 * @see http://wiki.vg/Protocol_version_numbers
	 */
	public List<Integer> getProtocol();
	
}
