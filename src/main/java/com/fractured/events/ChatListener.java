package com.fractured.events;

import com.fractured.team.Team;
import com.fractured.user.User;
import com.fractured.user.UserManager;
import com.fractured.util.Utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public final class ChatListener implements Listener
{
    @EventHandler
    public static void onChat(AsyncPlayerChatEvent event)
    {
        Player player = event.getPlayer();

        User user = UserManager.getUser(player.getUniqueId());
        Team team = user.getTeam();

        if (user.isInTeamChat())
        {
            // team chat
            event.setCancelled(true);
            team.alert(Utils.color(team.color() + "&l[" + team.getName().charAt(0) + "] &r" + player.getName() + ": &f" + event.getMessage()));
        } else
        {
            // public chat
            event.setFormat((team != null ? team.color() : ChatColor.GRAY) + Utils.color("%1$s&f: %2$s"));
        }
    }
}
