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
package com.mcmiddleearth.guidebook.command;

import com.mcmiddleearth.guidebook.data.InfoArea;
import com.mcmiddleearth.guidebook.data.PluginData;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.CommandSender;

/**
 *
 * @author Fraspace5
 */
public class GuidebookDisable extends GuidebookCommand {

    public GuidebookDisable(String... permissionNodes) {
        super(1, true, permissionNodes);
        setShortDescription(": Disable a guidebook");
        setUsageDescription(" To use that command type /guidebook disable guidebook");
    }

    //guidebook disable guidebookname
    //           0        1       
    @Override
    protected void execute(CommandSender cs, String... args) {

        if (args.length == 1) {

            if (PluginData.getInfoAreas().containsKey(args[0])) {

                InfoArea area = PluginData.getInfoArea(args[0]);
                area.statusOff();
                PluginData.getMessageUtil().sendInfoMessage(cs, "Guidebook area " + args[0] + " Disabled");
                try {
                    PluginData.saveArea(area);
                } catch (IOException ex) {
                    Logger.getLogger(GuidebookRename.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                PluginData.getMessageUtil().sendErrorMessage(cs, "This area doesn't exist");
            }

        } else {

            PluginData.getMessageUtil().sendErrorMessage(cs, "Invalid Usage! /guidebook disable guidebook");

        }

    }

}
