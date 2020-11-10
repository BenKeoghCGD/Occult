package com.AxiusDesigns.AxiusPlugins.Occult.Events;

import com.AxiusDesigns.AxiusPlugins.Occult.Occult;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class EventJoin implements Listener {

    public Occult plugin;
    public ArrayList<String> names = new ArrayList<String>();

    public EventJoin(Occult occult) {
        this.plugin = occult;
        names = occult.getHidden();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        EntityPlayer[] playerNMS = new EntityPlayer[names.size()];
        System.out.print(names);

        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            int current = 0;
            for(String name : names) {
                playerNMS[current] = ((CraftPlayer) Bukkit.getPlayer(name)).getHandle();
                current++;
            }

            PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playerNMS);
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        }, 1);
    }
}
