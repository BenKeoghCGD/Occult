package com.AxiusDesigns.AxiusPlugins.Occult.Commands;

import com.AxiusDesigns.AxiusPlugins.Occult.Occult;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.PacketPlayOutPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;

public class CommandOccult implements CommandExecutor {

    private Occult plugin;
    private HashMap<String, String> messages = new HashMap<String, String>();

    public CommandOccult(Occult occult, HashMap<String, String> messages) {
        this.plugin = occult;
        this.messages = messages;
    }

    public boolean onCommand(CommandSender sndr, Command command, String s, String[] args) {
        if(!sndr.hasPermission("Occult.Hide")) return false;
        if(args.length < 1) {
            sndr.sendMessage(parseString(messages.get("syntax")));
            return false;
        }
        else
        {
            if(Bukkit.getPlayer(args[0]) == null) {
                sndr.sendMessage(parseString(messages.get("missing-player")));
                return false;
            }
            if(plugin.checkName(args[0])) {
                sndr.sendMessage(parseString(messages.get("player-already-hidden").replaceAll("%player%", args[0])));
                return false;
            }
            else {
                Collection<? extends Player> players = Bukkit.getOnlinePlayers();
                EntityPlayer playersNMS = ((CraftPlayer) Bukkit.getPlayer(args[0])).getHandle();
                PacketPlayOutPlayerInfo packet = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, playersNMS);
                for(Player player : players) {
                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
                }
                sndr.sendMessage(parseString(messages.get("player-hidden").replaceAll("%player%", args[0])));
                plugin.addName(args[0]);
            }
        }
        return true;
    }

    private String parseString(String syntax) {
        return ChatColor.translateAlternateColorCodes('&', syntax);
    }
}
