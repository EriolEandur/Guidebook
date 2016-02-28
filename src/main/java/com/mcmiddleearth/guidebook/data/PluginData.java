/*
 * Copyright (C) 2016 MCME
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mcmiddleearth.guidebook.data;

import com.mcmiddleearth.guidebook.GuidebookPlugin;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Eriol_Eandur
 */
public class PluginData {
    
    
    @Getter
    private final static Map<String, InfoArea> infoAreas = new HashMap<>();
    
    private final static Set<UUID> recipients = new HashSet<>();
    
    @Getter
    private static final File dataFile = new File(GuidebookPlugin.getPluginInstance().getDataFolder(),
                                                  File.separator+"PluginData.yml");
    
    static {
        if(!GuidebookPlugin.getPluginInstance().getDataFolder().exists()) {
            GuidebookPlugin.getPluginInstance().getDataFolder().mkdirs();
        }
    }
    
    public static boolean isRecipient(Player player) {
        return recipients.contains(player.getUniqueId());
    }
    
    public static InfoArea addInfoArea(String name, InfoArea newArea) {
        return infoAreas.put(name, newArea);
    }
    
    public static InfoArea deleteInfoArea(String name) {
        return infoAreas.remove(name);
    }
    
    public static InfoArea getInfoArea(String name) {
        return infoAreas.get(name);
    }
    
    public static boolean hasInfoArea(String name) {
        return infoAreas.get(name)!=null;
    }
    
    public static void exclude(Player player) {
        recipients.remove(player.getUniqueId());
    }
    
    public static void include(Player player) {
        recipients.add(player.getUniqueId());
    }
    
    public static void saveData() throws IOException {
        FileConfiguration config = new YamlConfiguration();
        for(String areaName : infoAreas.keySet()) {
            config.set(areaName, infoAreas.get(areaName).serialize());
        }
        config.save(dataFile);    
    }
    
    public static void loadData() {
        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(dataFile);
            for(String areaName : config.getKeys(false)) {
                if(config.getConfigurationSection(areaName).contains("radius")) {
                    //infoAreas.put(areaName, 
                    //        new SphericalInfoArea(config.getConfigurationSection(areaName)));
                }
                else {
                    infoAreas.put(areaName, 
                            new CuboidInfoArea(config.getConfigurationSection(areaName)));
                }
            }
        } catch (IOException | InvalidConfigurationException ex) {
            Logger.getLogger(PluginData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
