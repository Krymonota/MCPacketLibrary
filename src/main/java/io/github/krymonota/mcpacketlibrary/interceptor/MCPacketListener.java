/**
 * @project: mcpacketlibrary
 * @package: io.github.krymonota.mcpacketlibrary.interceptor
 * @file: MCPacketListener.java
 * @author: Niklas (Krymonota)
 * @date: 25.10.2015
 */
package io.github.krymonota.mcpacketlibrary.interceptor;

import io.github.krymonota.mcpacketlibrary.interceptor.event.MCPacketReceiveEvent;
import io.github.krymonota.mcpacketlibrary.interceptor.event.MCPacketSendEvent;

public interface MCPacketListener {
	// The Bukkit Event API is too slow for packet handling and might therefore cause lags or errors.
	
	/**
	 * MCPacketReceiveEvent is called when a packet gets received (client -> server).
	 */
    public void onPacketReceive(MCPacketReceiveEvent event);

    /**
     * MCPacketSendEvent is called when a packet gets send (server -> client).
     */
    public void onPacketSend(MCPacketSendEvent event);
    
}
