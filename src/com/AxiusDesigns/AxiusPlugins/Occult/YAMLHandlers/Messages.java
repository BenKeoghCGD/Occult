package com.AxiusDesigns.AxiusPlugins.Occult.YAMLHandlers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.AxiusDesigns.AxiusPlugins.Occult.Occult;


public class Messages {

    Occult plugin;
    public HashMap<String, String> messageData;

    public Messages(Occult instance) {
        plugin = instance;
        this.messageData = new HashMap<String, String>();
    }

    public HashMap<String, String> getMessageData()
    {
        File f = new File(plugin.getDataFolder().getParentFile() + File.separator + "AxiusPlugins" + File.separator + "Occult" + File.separator + "messages.yml");
        saveMessages();
        if (!f.exists()) {
            try
            {
                f.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return loadMessages();
    }

    public HashMap<String, String> loadMessages()
    {
        File f = new File(plugin.getDataFolder().getParentFile() + File.separator + "AxiusPlugins" + File.separator + "Occult" + File.separator + "messages.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        for (String message : config.getConfigurationSection("").getKeys(true)) {
            this.messageData.put(message, config.getString(message));
        }
        return this.messageData;
    }

    private void saveMessages()
    {
        setMessage("syntax", "&6&lOccult&7 Incorrect Syntax: /Occult <player>");
        setMessage("missing-player", "&6&lOccult&7 Player not found.");
        setMessage("player-hidden", "&6&lOccult&7 Successfully hidden &6%player%&7 from tablist.");
        setMessage("player-shown", "&6&lOccult&7 Successfully shown &6%player%&7 on tablist.");
        setMessage("player-already-hidden", "&6&lOccult&7 player &6%player%&7 already hidden.");
        setMessage("player-not-hidden", "&6&lOccult&7 player &6%player%&7 not hidden.");
    }

    private void setMessage(String name, String message)
    {
        File f = new File(plugin.getDataFolder().getParentFile() + File.separator + "AxiusPlugins" + File.separator + "Occult" + File.separator + "messages.yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(f);
        if (!config.isSet(name))
        {
            config.set(name, message);
            try
            {
                config.save(f);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}