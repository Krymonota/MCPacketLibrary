/**
 * @project: mcpacketlibrary
 * @package: io.github.krymonota.mcpacketlibrary.packet
 * @file: MCPacketWrapper.java
 * @author: Niklas (Krymonota)
 * @date: 25.10.2015
 */
package io.github.krymonota.mcpacketlibrary.packet;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class MCPacketWrapper {

	/**
	 * @param IMCPacket The packets to send.
	 * @param Player The player to send the packets to.
	 * 
	 * @return True if the given packets's protocol number matches up with the given player's version number.
	 */
	public static boolean canSend(Player player, IMCPacket packet) {
		// Spigot 1.8 doesn't accept any other clients on other versions, so therefore just return true for now.
		// packet.getProtocol().contains(((CraftPlayer) player).getHandle().playerConnection.networkManager.getVersion());
		return true;
	}

	/**
	 * Sends all of the given packets to all of the given players.
	 */
	public static void send(Collection<? extends Player> players, List<IMCPacket> packets) {
		players.forEach(player -> packets.forEach(packet -> send(player, packet)));
	}
	
	/**
	 * Sends the given packet to all of the given players.
	 */
	public static void send(Collection<? extends Player> players, IMCPacket packet) {
		send(players, Arrays.asList(packet));
	}
	
	/**
	 * Performs the actual sending of the packets to the given target.
	 * 
	 * @param List<IMCPacket> The packets to send.
	 * @param Player The player to send the packets to.
	 */
	public static void send(Player player, List<IMCPacket> packets) {
		packets.forEach(packet -> send(player, packet));
	}

	/**
	 * Performs the actual sending of the packets to the given target.
	 * 
	 * @param IMCPacket The packets to send.
	 * @param Player The player to send the packets to.
	 */
	public static void send(Player player, IMCPacket packet) {
		if (canSend(player, packet))
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet.getPacket());
	}

	/**
	 * Clones the object.
	 * 
	 * @param Object The object to clone.
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static Object cloneObject(Object object) throws InstantiationException, IllegalAccessException {
		if (object == null)
			return object;

		Object clone = object.getClass().newInstance();

		for (Field f : object.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			f.set(clone, f.get(object));
		}

		return clone;
	}
	
}
