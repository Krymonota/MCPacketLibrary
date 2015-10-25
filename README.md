# MCPacketLibrary #
## Minecraft Packet Handling Library for Bukkit/Spigot ##

**Note:** This plugin is for developers only! If you're a server owner, this plugin will have no use for you, unless a plugin requires it. If that's the case, you will only have to install it on your server and that's it!
If you're a developer, keep reading to see how you can make use of this library.

- [Download](https://github.com/Krymonota/MCPacketLibrary/releases)
- [Maven](https://mymavenrepo.com/repo/v3i97KuHAZF1V0yF9mn0/com/craftapi/mcpacketlibrary/)


---


### Packet Interceptor ###
You can intercept packets, change their data and even prevent from sending or receiving them.

#### Creating Listeners ####
```java
package PACKAGE;

import net.minecraft.server.PACKAGE_VERSION.Packet;

import org.bukkit.entity.Player;

import com.craftapi.mcpacketlibrary.interceptor.MCPacketListener;
import com.craftapi.mcpacketlibrary.interceptor.event.MCPacketReceiveEvent;
import com.craftapi.mcpacketlibrary.interceptor.event.MCPacketSendEvent;

public class PacketListener implements MCPacketListener {

	@Override
	public void onReceive(MCPacketReceiveEvent event) {
		Player player = event.getPlayer();
		Packet packet = event.getPacket();
		// Do something with the packet
	}

	@Override
	public void onSend(MCPacketSendEvent event) {
		Player player = event.getPlayer();
		Packet packet = event.getPacket();
		// Do something with the packet
	}

}
```

#### Registering Listeners ####
```java
package PACKAGE;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.craftapi.mcpacketlibrary.MCPacketLibrary;

import PACKAGE.PacketListener;

public class PacketPlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		MCPacketLibrary.getPacketManager().registerListener(new PacketListener());
	}
	
}
```


---


### Packet Service ###
You can wrap packets and make sure that they get only sent to players with a defined [Protocol Version](http://wiki.vg/Protocol_version_numbers).

#### Wrapping Packets ####
```java
package PACKAGE;

import java.util.Arrays;
import java.util.List;

import net.minecraft.server.PACKAGE_VERSION.Packet;
import net.minecraft.server.PACKAGE_VERSION.PacketPlayInClientCommand;
import net.minecraft.server.PACKAGE_VERSION.PacketPlayInClientCommand.EnumClientCommand;

import com.craftapi.mcpacketlibrary.packet.IMCPacket;

public class RespawnPacket implements IMCPacket {

	@Override
	public Packet<?> getPacket() {
		PacketPlayInClientCommand packet = new PacketPlayInClientCommand(EnumClientCommand.PERFORM_RESPAWN);
		
		return packet;
	}

	@Override
	public List<Integer> getProtocol() {
		return Arrays.asList(47);
	}

}
```

### Sending a wrapped packet ###
```java
package PACKAGE;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.craftapi.mcpacketlibrary.packet.MCPacketService;

import PACKAGE.RespawnPacket;

public class PlayerDeathListener implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		
		new BukkitRunnable() {
			@Override
			public void run() {
				if (player != null)
					MCPacketService.send(player, new RespawnPacket());
			}
		}.runTaskLater(PLUGIN, 1L);
	}
	
}
```
