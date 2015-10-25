package com.craftapi.mcpacketlibrary.interceptor;

import com.craftapi.mcpacketlibrary.interceptor.event.MCPacketReceiveEvent;
import com.craftapi.mcpacketlibrary.interceptor.event.MCPacketSendEvent;

public interface MCPacketListener {
	// The Bukkit Event API is too slow for packet handling and might therefore cause lags or errors.
	
	/**
	 * MCPacketReceiveEvent is called when a packet gets received (client -> server).
	 */
    public void onReceive(MCPacketReceiveEvent event);

    /**
     * MCPacketSendEvent is called when a packet gets send (server -> client).
     */
    public void onSend(MCPacketSendEvent event);
    
}
