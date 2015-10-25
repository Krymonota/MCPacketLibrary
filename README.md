# MCPacketLibrary #
**Minecraft Packet Handling Library**

This plugin is for developers only. If you're a server owner, this plugin will have no use for you, unless a plugin requires it.
If that's the case, you will only have to install it on your server and that's it!

If you're a developer, keep reading to see how you can make use of this library.
---

## Packet Interceptor ##
You can intercept packets, change their data and even prevent from sending or receiving them.

### Creating listeners ###
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
	}

	@Override
	public void onSend(MCPacketSendEvent event) {
		Player player = event.getPlayer();
		Packet packet = event.getPacket();
	}

}
```

### Registering listeners ###
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

