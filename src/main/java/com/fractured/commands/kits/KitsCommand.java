package com.fractured.commands.kits;

import com.fractured.FracturedCore;
import com.fractured.kits.KitManager;
import com.fractured.util.globals.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class KitsCommand
{

    private KitsCommand()
    {
    }

    public static boolean kits(final CommandSender sender, final Command cmd, final String label, final String[] args)
    {
        if (!(sender instanceof Player player))
        {
            sender.sendMessage(FracturedCore.getMessages().get(Messages.COMMAND_CONSOLE_BLOCKED));
            return true;
        }

        KitManager.showGUI(player);
        return true;
    }
}
