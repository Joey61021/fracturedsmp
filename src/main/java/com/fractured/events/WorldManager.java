package com.fractured.events;

import com.fractured.FracturedCore;
import com.fractured.config.Config;
import com.fractured.team.Claim;
import com.fractured.team.ClaimManager;
import com.fractured.team.Team;
import com.fractured.user.User;
import com.fractured.user.UserManager;
import com.fractured.util.Utils;
import com.fractured.util.globals.Messages;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class WorldManager implements Listener
{
    public static final World OVER_WORLD;

    public static Location SPAWN_POS1;
    public static Location SPAWN_POS2;

    private static final String DEFAULT_WORLD_PATH = "locations.over_world";
    private static final String SPAWN_POS1_PATH = "locations.spawn_pos1";
    private static final String SPAWN_POS2_PATH = "locations.spawn_pos2";

    static
    {
        Config config = FracturedCore.getFracturedConfig();

        OVER_WORLD = Bukkit.getWorld(config.getString(DEFAULT_WORLD_PATH));

        if (OVER_WORLD == null)
        {
            throw new IllegalArgumentException("Unable to find the over world world at " + DEFAULT_WORLD_PATH);
        } else
        {
            SPAWN_POS1 = getLocation(config, SPAWN_POS1_PATH);
            SPAWN_POS2 = getLocation(config, SPAWN_POS2_PATH);
        }
    }

    public static boolean isInSpawn(Location location)
    {
        return isInRegion(location, SPAWN_POS1, SPAWN_POS2);
    }

    private static boolean isInRegion(Location loc, Location pos1, Location pos2)
    {
        if (loc.getWorld() != WorldManager.getSpawn().getWorld())
        {
            return false;
        }

        double minX = Math.min(pos1.getX(), pos2.getX());
        double maxX = Math.max(pos1.getX(), pos2.getX());
        double minY = Math.min(pos1.getY(), pos2.getY());
        double maxY = Math.max(pos1.getY(), pos2.getY());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        return pos1.getWorld().equals(loc.getWorld()) && loc.getX() > minX && loc.getX() < maxX && loc.getY() > minY && loc.getY() < maxY && loc.getZ() > minZ && loc.getZ() < maxZ;
    }

    public static Location getLocation(Config config, String path)
    {
        String worldString = config.getString(path + ".world");
        World world;

        if (worldString == null)
        {
            world = OVER_WORLD;
        } else
        {
            world = Bukkit.getWorld(worldString);

            if (world == null)
            {
                world = OVER_WORLD;
                Bukkit.getLogger().warning("Unable to find world " + worldString + " from " + path);
            }
        }

        return new Location(world,
                config.getDouble(path + ".x", 0.0),
                config.getDouble(path + ".y", 64.0),
                config.getDouble(path + ".z", 0.0),
                (float) config.getDouble(path + ".yaw", 0.0),
                (float) config.getDouble(path + ".pitch", 0.0));
    }

    public static boolean setLocation(Config settings, String key, Location location) {
        if (!settings.isSet(key)) {
            return false;
        }

        settings.set(key + ".x", location.getX());
        settings.set(key + ".y", location.getY());
        settings.set(key + ".z", location.getZ());
        settings.set(key + ".yaw", location.getYaw());
        settings.set(key + ".pitch", location.getPitch());
        settings.set(key + ".world", location.getWorld().getName());
        settings.save();
        return true;
    }

    /**
     * Generates a team border from -x to +x starting at the location (x0, y0, z0) in the world, with the specified
     * materials on the left and right, and bedrock in the middle.
     * @param world not null
     */
    private static void generateXBorder(final int dist, int x0, int y0, int z0, World world, Material left, Material right)
    {
        // .  .  .  .  .  v  @  @  @  @  @  @  @  @  @  @  ^ -z
        // .  .  .  .  .  .  1  1  1  1  1  1  1  1  1  1  |
        // .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
        // .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
        // .  .  .  .  .  .  2  2  2  2  2  2  2  2  2  2  |
        // .  .  .  .  .  v  @  @  @  @  @  @  @  @  @  @  |
        // .  .  .  .  .  .  1  1  1  1  1  1  1  1  1  1  |
        // .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
        // .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  | v = (x0, z0) % 16   [for this case, v = (5,5)]
        // .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
        // .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
        // .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
        // .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
        // .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
        // .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  |
        // .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  V +z
        // <  -  -  -  -  -  -  -  -  -  -  -  -  -  -  >
        // -x                                          +x

        /* Where the player is in the chunk relative to the 0 corner of it.
           (THIS IS NOT THE SAME AS CHUNK COORDINATE, THESE SCALE THE SAME
           AS REGULAR COORDINATES) */
        // Relative coordinates
        int rx = x0 % 16;
        int rz = z0 % 16;

        if (rx < 0)
        {
            rx += 16;
        }

        if (rz < 0)
        {
            rz += 16;
        }

        // Chunk coordinates
        int cx = x0 >> 4; // divide by 16
        int cz = z0 >> 4; // divide by 16

        if (rz == 0)
        {
            // -z edge (We need to load 2 chunks)

            Chunk bedrock = world.getChunkAt(cx, cz);
            Chunk adjacent = world.getChunkAt(cx, cz - 1);

            // Less than one chunk
            if (rx + dist < 16)
            {

            }

            for (int x = rx + 1; x <= Math.min(15, rx + dist); ++x)
            {
                for (int y = world.getMinHeight() + 1; y <= y0; ++y)
                {
                    adjacent.getBlock(rx, y, 15).setType(left);
                    bedrock.getBlock(rx, y, 0).setType(Material.BEDROCK);
                    bedrock.getBlock(rx, y, 1).setType(right);
                }
            }
        } else if (rz == 15)
        {
            // +z edge (We need to load 2 chunks)

            Chunk bedrock = world.getChunkAt(cx, cz);
            Chunk adjacent = world.getChunkAt(cx, cz + 1);
        } else
        {
            Chunk chunk = world.getChunkAt(cx, cz);

            int rxAndX;

            for (int x = 1; x <= Math.min(15 - rx, dist); ++x)
            {
                rxAndX = rx + x;

                for (int y = world.getMinHeight() + 1; y <= y0; ++y)
                {
                    chunk.getBlock(rxAndX, y, rz - 1).setType(left);
                    chunk.getBlock(rxAndX, y, rz).setType(Material.BEDROCK);
                    chunk.getBlock(rxAndX, y, rz + 1).setType(right);
                }
            }

            int i;

            // Start at the next chunk, we already did one
            // (dist - rx) >> 4 is how many chunks we need to decorate.
            for (i = 1; i <= (dist - rx) >> 4; ++i)
            {
                chunk = world.getChunkAt(cx + i, cz);

                for (int x = 0; x < 16; ++x)
                {
                    for (int y = world.getMinHeight() + 1; y <= y0; ++y)
                    {
                        chunk.getBlock(x, y, rz - 1).setType(left);
                        chunk.getBlock(x, y, rz).setType(Material.BEDROCK);
                        chunk.getBlock(x, y, rz + 1).setType(right);
                    }
                }
            }

            chunk = world.getChunkAt(cx + i, cz);

            for (int x = 0; x < (dist - (15 - rx)) % 16; ++x)
            {
                for (int y = world.getMinHeight() + 1; y <= y0; ++y)
                {
                    chunk.getBlock(x, y, rz - 1).setType(left);
                    chunk.getBlock(x, y, rz).setType(Material.BEDROCK);
                    chunk.getBlock(x, y, rz + 1).setType(right);
                }
            }
        }
    }

    /**
     * Generates a team border from -x to +x starting at the location (x0, y0, z0) in the world, with the specified
     * materials on the left and right, and bedrock in the middle.
     * @param world not null
     */
    private static void generateZBorder(int dist, int x0, int y0, int z0, World world, Material left, Material right)
    {

    }

    /**
     * This method does two things,
     * 1) Generates new team borders and sets the world border
     * 2) Reloads team claims by recommitting them to the database and then reloading the ClaimManager completely.
     * @param radius Positive
     */
    public static void generateTeamBorders(int radius, Location location)
    {
        World world = location.getWorld();

        if (world == null)
        {
            throw new IllegalStateException("Cannot generate team borders in a null world!");
        }

        int x0 = location.getBlockX();
        int y0 = location.getBlockY() - 1;
        int z0 = location.getBlockZ();

        // . . . | . . . x = start generating
        // . . . | . . .
        // . . . x . . .
        // x - - + x - -
        // . . . | . . .
        // . . . | . . .
        // . . . x . . .

        generateXBorder(radius, x0, y0, z0, world, Material.RED_CONCRETE, Material.BLUE_CONCRETE);
        generateZBorder(radius, x0, y0, z0, world, Material.BLUE_CONCRETE, Material.YELLOW_CONCRETE);
        generateXBorder(radius, x0 - radius - 1, y0, z0, world, Material.GREEN_CONCRETE, Material.YELLOW_CONCRETE);
        generateZBorder(radius, x0, y0, z0 - radius - 1, world, Material.RED_CONCRETE, Material.GREEN_CONCRETE);

        // Generate iron blocks
        for (int x = 0; x < 3; x++)
        {
            for (int z = 0; z < 3; z++)
            {
                world.getBlockAt(x0 - 1 + x, y0 - 2, z0 - 1 + z).setType(Material.IRON_BLOCK);
            }
        }

        // Set world border
        world.getWorldBorder().setCenter(new Location(world, x0 + 0.5, y0, z0 + 0.5));
        world.getWorldBorder().setSize(2 * radius + 1);
    }

    public static void extendTeamBorders(int size)
    {
//        for (int a = 0; a < BEACON.getBlockY() + 1 - MIN_HEIGHT; a++)
//        {
//            world.getBlockAt(x, y - a, z).setType(Material.BEDROCK);
//
//            for (int b = 0; b < radius; b++)
//            {
//                world.getBlockAt(x + 1 + b, y - a, z - 1).setType(Material.RED_CONCRETE);
//                world.getBlockAt(x + 1 + b, y - a, z).setType(Material.BEDROCK);
//                world.getBlockAt(x + 1 + b, y - a, z + 1).setType(Material.BLUE_CONCRETE);
//            }
//
//            for (int b = 0; b < radius; b++)
//            {
//                world.getBlockAt(x - 1, y - a, z + 1 + b).setType(Material.YELLOW_CONCRETE);
//                world.getBlockAt(x, y - a, z + 1 + b).setType(Material.BEDROCK);
//                world.getBlockAt(x + 1, y - a, z + 1 + b).setType(Material.BLUE_CONCRETE);
//            }
//
//            for (int b = 0; b < radius; b++)
//            {
//                world.getBlockAt(x - 1 - b, y - a, z - 1).setType(Material.LIME_CONCRETE);
//                world.getBlockAt(x - 1 - b, y - a, z).setType(Material.BEDROCK);
//                world.getBlockAt(x - 1 - b, y - a, z + 1).setType(Material.YELLOW_CONCRETE);
//            }
//
//            for (int b = 0; b < radius; b++)
//            {
//                world.getBlockAt(x - 1, y - a, z - 1 - b).setType(Material.LIME_CONCRETE);
//                world.getBlockAt(x, y - a, z - 1 - b).setType(Material.BEDROCK);
//                world.getBlockAt(x + 1, y - a, z - 1 - b).setType(Material.RED_CONCRETE);
//            }
//        }
    }

    public static Location getSpawn()
    {
        return OVER_WORLD.getSpawnLocation();
    }

    private static <E extends BlockEvent & Cancellable> void onBlockChange(Player player, E event)
    {
        if (player.getGameMode() == GameMode.CREATIVE || UserManager.getUser(player.getUniqueId()).getBypassRegions())
        {
            return;
        }

        Location loc = event.getBlock().getLocation();

        if (isInSpawn(loc))
        {
            event.setCancelled(true);
            return;
        }

        User user = UserManager.getUser(player.getUniqueId());
        Team team = user.getTeam();

        // The player can't do anything without a team
        if (team == null)
        {
            event.setCancelled(true);
            return;
        }

        Claim claim = ClaimManager.getClaim(loc);

        // The behavior here is changed slightly from the original.
        // If the enemy team is null, the event will not be cancelled, the original did cancel these events.
        if (claim != null && claim.getTeam() != team)
        {
            if (claim.getShield())
            {
                //todo fixme config messages
                player.sendMessage(Utils.color("&cThis region is protected by a shield!"));
                event.setCancelled(true);
                return;
            }

            if (claim.getTeam().isOffline())
            {
                event.setCancelled(true);
                player.sendMessage(FracturedCore.getMessages().get(Messages.REGION_TEAM_OFFLINE));
            } else
            {
                // Alert the enemy team
                //todo fixme config messages
                claim.getTeam().alert("A block was changed in your claim at (" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")!");
            }
        }
    }

    @EventHandler
    public static void onFood(FoodLevelChangeEvent event)
    {
        Random rand = new Random(); // Reduce hunger depletion rate by 50%
        if (event.getEntity().getFoodLevel() > event.getFoodLevel() && rand.nextDouble() >= 0.5)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void onSpawn(CreatureSpawnEvent event)
    {
        if (isInSpawn(event.getLocation()))
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public static void onTeleport(EntityTeleportEvent event)
    {
        if (isInSpawn(event.getTo()))
        {
            event.setCancelled(true); // Prevent ender pearls into spawn region
        }
    }

    @EventHandler
    public static void onMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        User user = UserManager.getUser(player.getUniqueId());

        if (user == null)
        {
            return;
        }

        Team team = user.getTeam();
        if (team != null)
        {
            return;
        }

        if (player.getLocation().distance(getSpawn()) > 50)
        {
            player.teleport(getSpawn());
        }
    }

    @EventHandler
    public static void onBreak(BlockBreakEvent event)
    {
        onBlockChange(event.getPlayer(), event);
    }

    @EventHandler
    public static void onPlace(BlockPlaceEvent event)
    {
        onBlockChange(event.getPlayer(), event);
    }

    @EventHandler
    public static void onInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE)
        {
            return;
        }

        User user = UserManager.getUser(player.getUniqueId());
        Team team = user.getTeam();

        // The player can't do anything without a team
        if (team == null)
        {
            event.setCancelled(true);
            return;
        }

        Block clicked = event.getClickedBlock();

        if (clicked == null || clicked.getType() == Material.AIR)
        {
            return;
        }

        ItemStack item = event.getItem();

        if (item == null || item.getType().equals(Material.AIR))
        {
            return;
        }

        if (item.getType().name().contains("helmet") && event.getAction().name().toLowerCase().contains("right"))
        {
            event.setCancelled(true);
            return;
        }

        Location loc = event.getClickedBlock().getLocation();
        Claim claim = ClaimManager.getClaim(loc);

        // The behavior here is changed slightly from the original.
        // If the enemy team is null, the event will not be cancelled, the original did cancel these events.
        if (!user.getBypassRegions() && claim != null && claim.getTeam() != team)
        {
            if (claim.getShield())
            {
                //todo fixme config messages
                player.sendMessage(Utils.color("&cThis region is protected by a shield!"));
                event.setCancelled(true);
                return;
            }

            if (claim.getTeam().isOffline())
            {
                event.setCancelled(true);
                player.sendMessage(FracturedCore.getMessages().get(Messages.REGION_TEAM_OFFLINE));
            } else
            {
                // If last alert location is less than 5 blocks away, cancel to prevent spam
                if (user.getLastAlert() != null && loc.distance(user.getLastAlert()) < 10)
                {
                    return;
                }
                // Alert the enemy team
                user.setLastAlert(loc);
                claim.getTeam().alert("&cThere is activity in your claim at (" + loc.getBlockX() + ", " + loc.getBlockY() + ", " + loc.getBlockZ() + ")!");
            }
        }
    }
}
