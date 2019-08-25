/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcmiddleearth.guidebook.command;

import com.mcmiddleearth.guidebook.data.PluginData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Eriol_Eandur
 */
public class GuidebookOn extends GuidebookCommand{
    
    public GuidebookOn(String... permissionNodes) {
        super(0, true, permissionNodes);
        setShortDescription(": Includes a player to receipients of Guidebook messages.");
        setUsageDescription(": The player issueing this command will receive Guidebook messages.");
    }
    
    @Override
    protected void execute(CommandSender cs, String... args) {
        Player player = (Player)cs;
        if(args.length>0 && player.isOp() && args[0].equals("all")) {
            PluginData.setAllOff(false);
            PluginData.getMessageUtil().sendInfoMessage(cs, "Guidebook messages enabled.");
            return;
        }
        PluginData.include(player);
        PluginData.getMessageUtil().sendInfoMessage(cs, "You will now receive info messages from Guidebook.");
    }
    
}
