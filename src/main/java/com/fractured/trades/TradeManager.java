package com.fractured.trades;

import com.fractured.FracturedCore;
import com.fractured.util.globals.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class TradeManager {

    public static Set<TradeRequest> requests = new HashSet<>();

    public static int COOLDOWN = 60000; // One minute

    public static TradeRequest getActiveRequest(Player player)
    {
        TradeRequest tradeRequest = null;
        for (TradeRequest request : requests)
        {
            if (!request.getRequester().equals(player.getUniqueId()))
            {
                continue;
            }

            tradeRequest = request;
        }

        return tradeRequest;
    }

    public static void startTrade(Player player, Player target)
    {
        TradeRequest activeRequest = getActiveRequest(player);
        if (activeRequest != null && System.currentTimeMillis() - activeRequest.getTimestamp() < COOLDOWN)
        {
            player.sendMessage(FracturedCore.getMessages().get(Messages.COMMAND_TRADE_ALREADY_ACTIVE).replace("%time%", COOLDOWN - (System.currentTimeMillis() - activeRequest.getTimestamp()) + "s"));
            return;
        }

        player.sendMessage(FracturedCore.getMessages().get(Messages.COMMAND_TRADE_SENT).replace("%player%", target.getDisplayName()));
        target.sendMessage(FracturedCore.getMessages().get(Messages.COMMAND_TRADE_RECEIVED).replace("%player%", player.getDisplayName()));

        requests.removeIf(request -> request.getRequester().equals(player.getUniqueId()));
        requests.add(new TradeRequest(player.getUniqueId(), target.getUniqueId()));

        openInventory(player, target);
    }

    public static void openInventory(Player player, Player target)
    {
        Inventory inv = Bukkit.createInventory(null, 6*9, player.getName() + " / " + target.getName());

        ItemStack divider = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta dividerMeta = divider.getItemMeta();

        if (dividerMeta != null)
        {
            dividerMeta.setDisplayName(" ");
            divider.setItemMeta(dividerMeta);
        }

        for (int i = 0; i < 6; i++)
        {
            inv.setItem(4 + (i * 9), divider);
        }

        player.openInventory(inv);
        target.openInventory(inv);
    }
}
