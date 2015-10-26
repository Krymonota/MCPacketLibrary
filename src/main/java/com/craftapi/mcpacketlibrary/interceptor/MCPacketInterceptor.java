/**
 * @project: mcpacketlibrary
 * @package: com.craftapi.mcpacketlibrary.interceptor
 * @file: MCPacketInterceptor.java
 * @author: Niklas (Krymonota)
 * @date: 25.10.2015
 */
package com.craftapi.mcpacketlibrary.interceptor;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_8_R3.Packet;

import org.bukkit.entity.Player;

import com.craftapi.mcpacketlibrary.MCPacketLibrary;

public class MCPacketInterceptor extends ChannelDuplexHandler {

	private Player player;

	public MCPacketInterceptor(Player player) {
		this.player = player;
	}

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		if (MCPacketLibrary.getPacketManager().willSend(this.player, (Packet<?>) msg))
			super.write(ctx, msg, promise);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (MCPacketLibrary.getPacketManager().canIncome(this.player, (Packet<?>) msg))
			super.channelRead(ctx, msg);
	}

}
