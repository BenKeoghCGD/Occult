package com.AxiusDesigns.AxiusPlugins.Occult;

import com.AxiusDesigns.AxiusPlugins.Occult.Commands.CommandOccult;
import com.AxiusDesigns.AxiusPlugins.Occult.Commands.CommandUnOccult;
import com.AxiusDesigns.AxiusPlugins.Occult.Events.EventJoin;
import com.AxiusDesigns.AxiusPlugins.Occult.YAMLHandlers.Messages;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Occult extends JavaPlugin {
    public String prefix = "[Occult] ";
    public Messages messages;
    public HashMap<String, String> messageData = new HashMap<String, String>();
    public ArrayList<String> hiddenPlayers = new ArrayList<String>();

    @Override
    public void onEnable() {
        System.out.print(prefix + "Enabling plugin.");

        //FILES
        System.out.print("- Checking files (1/2)");
        File data = new File(this.getDataFolder().getParentFile() + File.separator + "AxiusPlugins");
        if(!data.exists()) {
            System.out.print("- File not found, creating (1/2)");
            data.mkdir();
        }
        else System.out.print("- File found (1/2)");

        System.out.print("- Checking files (2/2)");
        File rph = new File(data + File.separator + "Occult");
        if(!rph.exists()) {
            System.out.print("- File not found, creating (2/2)");
            rph.mkdir();
        }
        else System.out.print("- File found (2/2)");

        //CONF/MESSAGES
        System.out.print("- Loading messages.yml");
        this.messages = new Messages(this);
        this.messageData = this.messages.getMessageData();

        //COMMANDS
        getCommand("Occult").setExecutor(new CommandOccult(this, messageData));
        getCommand("UnOccult").setExecutor(new CommandUnOccult(this, messageData));

        //EVENTS
        getServer().getPluginManager().registerEvents(new EventJoin(this), this);

        System.out.print(prefix + "Plugin enabled.");
    }

    public void addName(String name) {
        hiddenPlayers.add(name);
    }

    public void removeName(String name) {
        hiddenPlayers.remove(name);
    }

    public boolean checkName(String name) {
        return hiddenPlayers.contains(name);
    }

    public ArrayList<String> getHidden() {
        return hiddenPlayers;
    }
}
