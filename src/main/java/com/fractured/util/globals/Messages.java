package com.fractured.util.globals;

import com.fractured.config.keys.TextConfigKey;
import net.md_5.bungee.api.ChatColor;

import static com.fractured.config.DataSupplier.textKey;

public final class Messages
{
    private Messages()
    {

    }

    public static final TextConfigKey TEAM_INVENTORY_CLOSED = textKey("generic.team_inventory_closed");
    public static final TextConfigKey FRIENDLY_FIRE_DISABLED = textKey("generic.friendly_fire_disabled");
    public static final TextConfigKey CANNOT_SLEEP_HERE = textKey("generic.cannot_sleep_here");
    public static final TextConfigKey ENEMY_TEAM_BLOCKED = textKey("generic.enemy_team_blocked");
    public static final TextConfigKey MUST_BE_INT = textKey("generic.must_be_int");
    public static final TextConfigKey NO_PLAYER = textKey("generic.no_player");
    public static final TextConfigKey TELEPORTING = textKey("generic.teleporting");
    public static final TextConfigKey PROCESS_COMMAND = textKey("generic.process_command");
    public static final TextConfigKey CHAT_COOLDOWN = textKey("generic.chat_cooldown");

    public static final TextConfigKey REGION_SPAWN_REGION = textKey("generic.region.spawn_region");
    public static final TextConfigKey REGION_TEAM_ALERTED = textKey("generic.region.team_alerted");
    public static final TextConfigKey REGION_TEAM_OFFLINE = textKey("generic.region.team_offline");
    public static final TextConfigKey REGION_ALERT_BLOCK_PLACE = textKey("generic.region.block_place");
    public static final TextConfigKey REGION_ALERT_BLOCK_BREAK = textKey("generic.region.block_break");
    public static final TextConfigKey REGION_ALERT_CHEST_OPEN = textKey("generic.region.chest_open");

    public static final TextConfigKey UPGRADES_MAX_LEVEL = textKey("upgrades.max_level");
    public static final TextConfigKey UPGRADES_NOT_ENOUGH = textKey("upgrades.not_enough");
    public static final TextConfigKey UPGRADES_UPGRADED = textKey("upgrades.upgraded");

    public static final TextConfigKey COMMAND_INVALID_TARGET = textKey("commands.invalid_target");
    public static final TextConfigKey INVALID_WORLD = textKey("commands.invalid_world");
    public static final TextConfigKey COMMAND_INVALID_TEAM = textKey("commands.invalid_team");
    public static final TextConfigKey COMMAND_NO_PERMISSION = textKey("commands.no_permission");
    public static final TextConfigKey COMMAND_CONSOLE_BLOCKED = textKey("commands.console_blocked");
    public static final TextConfigKey COMMAND_NO_TEAM_BLOCKED = textKey("commands.no_team_blocked");
    public static final TextConfigKey COMMAND_TARGET_ALREADY_IN_TEAM = textKey("commands.target_already_in_team");
    public static final TextConfigKey COMMAND_TEAM_ALREADY_IN_TEAM = textKey("commands.already_in_team");

    public static final TextConfigKey COMMAND_CUSTOM_ENCHANT_USAGE = textKey("commands.custom_enchant.usage");
    public static final TextConfigKey COMMAND_CUSTOM_ENCHANT_UNKNOWN = textKey("commands.custom_enchant.unknown");
    public static final TextConfigKey COMMAND_CUSTOM_ENCHANT_INVALID_LEVEL = textKey("commands.custom_enchant.invalid_level");
    public static final TextConfigKey COMMAND_CUSTOM_ENCHANT_BAD_ITEM_TYPE = textKey("commands.custom_enchant.bad_item_type");
    public static final TextConfigKey COMMAND_CUSTOM_ENCHANT_LEVEL_BOUNDS = textKey("commands.custom_enchant.level_bounds");
    public static final TextConfigKey COMMAND_CUSTOM_ENCHANT_APPLIED = textKey("commands.custom_enchant.applied");

    public static final TextConfigKey COMMAND_TEAM_TELEPORT_USAGE = textKey("commands.team.teleport.usage");
    public static final TextConfigKey COMMAND_TEAM_REMOVE_USAGE = textKey("commands.team.remove.usage");
    public static final TextConfigKey COMMAND_TEAM_REMOVE_NOT_IN_TEAM = textKey("commands.team.remove.not_in_team");
    public static final TextConfigKey COMMAND_TEAM_REMOVE_TARGET_REMOVED = textKey("commands.team.clear.target_removed");
    public static final TextConfigKey COMMAND_TEAM_REMOVE_REMOVED = textKey("commands.team.clear.removed");
    public static final TextConfigKey COMMAND_TEAM_SET_USAGE = textKey("commands.team.set.usage");
    public static final TextConfigKey COMMAND_TEAM_FORCE_SET_USAGE = textKey("commands.team.force_set.usage");
    public static final TextConfigKey COMMAND_TEAM_FORCE_SET_INVALID_TEAM = textKey("commands.team.force_set.invalid_team");
    public static final TextConfigKey COMMAND_TEAM_JOIN_USAGE = textKey("commands.team.join.usage");
    public static final TextConfigKey COMMAND_TEAM_GENERAL_USAGE = textKey("commands.team.usage");
    public static final TextConfigKey COMMAND_TEAM_GENERAL_STAFF_USAGE = textKey("commands.team.staff_usage");
    public static final TextConfigKey COMMAND_TEAM_SET_SPAWN_USAGE = textKey("commands.team.setspawn.usage");
    public static final TextConfigKey COMMAND_TEAM_SET_SPAWN_SET = textKey("commands.team.setspawn.set");

    public static final TextConfigKey COMMAND_WORLD_USAGE = textKey("commands.world.usage");
    public static final TextConfigKey COMMAND_WORLD_TELEPORT_USAGE = textKey("commands.world.teleport.usage");
    public static final TextConfigKey COMMAND_WORLD_GENERATE_USAGE = textKey("commands.world.generate.usage");
    public static final TextConfigKey COMMAND_WORLD_GENERATE_WORLD_ALREADY_EXISTS = textKey("commands.world.generate.world_already_exists");
    public static final TextConfigKey COMMAND_WORLD_GENERATE_CONFIRM = textKey("commands.world.generate.confirm");
    public static final TextConfigKey COMMAND_WORLD_GENERATE_BEGIN = textKey("commands.world.generate.begin");
    public static final TextConfigKey COMMAND_WORLD_GENERATE_END = textKey("commands.world.generate.end");

    public static final TextConfigKey COMMAND_TEAM_CHAT_TOGGLE_ON = textKey("commands.team_chat.toggle_on");
    public static final TextConfigKey COMMAND_TEAM_CHAT_TOGGLE_OFF = textKey("commands.team_chat.toggle_off");

    public static final TextConfigKey CMD_SET_LOCATION_INVALID = textKey("commands.set_location.invalid");
    public static final TextConfigKey CMD_SET_LOCATION_SET = textKey("commands.set_location.set");

    public static final TextConfigKey CMD_SPAWN_NOT_IN_REGION = textKey("commands.spawn.not-in-region");
    public static final TextConfigKey CMD_SPAWN_TELEPORTED = textKey("commands.spawn.teleported");

    public static final TextConfigKey COMMAND_SETTINGS_CONSOLE_USAGE = textKey("commands.settings.console_usage");
    public static final TextConfigKey COMMAND_SETTINGS_PLAYER_USAGE = textKey("commands.settings.player_usage");
    public static final TextConfigKey COMMAND_SETTINGS_SET = textKey("commands.settings.set");
    public static final TextConfigKey COMMAND_SETTINGS_SET_INVALID_PATH = textKey("commands.settings.set.invalid_path");

    public static final TextConfigKey COMMAND_DISCORD = textKey("commands.discord");

    public static final TextConfigKey COMMAND_TPA_USAGE = textKey("commands.tpa.usage");
    public static final TextConfigKey COMMAND_TPA_ALREADY_SENT = textKey("commands.tpa.already_sent");
    public static final TextConfigKey COMMAND_TPA_SENT = textKey("commands.tpa.sent");
    public static final TextConfigKey COMMAND_TPA_RECEIVED = textKey("commands.tpa.received");
    public static final TextConfigKey COMMAND_TPA_ACCEPTED = textKey("commands.tpa.accepted");

    public static final TextConfigKey COMMAND_EVENT_USAGE = textKey("commands.event.usage");
    public static final TextConfigKey COMMAND_EVENT_NONE_ACTIVE = textKey("commands.event.no_event_active");
    public static final TextConfigKey COMMAND_EVENT_INVALID = textKey("commands.event.invalid_event");
    public static final TextConfigKey COMMAND_EVENT_RUN = textKey("commands.event.event_run");
    public static final TextConfigKey COMMAND_EVENT_ACTIVE = textKey("commands.event.event_active");
    public static final TextConfigKey COMMAND_EVENT_STOPPED = textKey("commands.event.event_stopped");
    public static final TextConfigKey COMMAND_EVENT_STOPPED_OFFLINE = textKey("commands.event.event_stopped_offline");

    public static final TextConfigKey COMMAND_BYPASS_REGIONS_TOGGLE_OFF = textKey("commands.bypass_regions.toggle_off");
    public static final TextConfigKey COMMAND_BYPASS_REGIONS_TOGGLE_ON = textKey("commands.bypass_regions.toggle_on");

    public static final TextConfigKey COMMAND_CREATEKIT_USAGE = textKey("commands.createkit.usage");
    public static final TextConfigKey COMMAND_CREATEKIT_ALREADY_EXISTS = textKey("commands.createkit.already_exists");
    public static final TextConfigKey COMMAND_CREATEKIT_SAVED = textKey("commands.createkit.saved");

    public static final TextConfigKey COMMAND_KIT_AVAILABLE = textKey("commands.kit.available");
    public static final TextConfigKey COMMAND_KIT_ON_COOLDOWN = textKey("commands.kit.on_cooldown");
    public static final TextConfigKey COMMAND_KIT_RECEIVED = textKey("commands.kit.received");
    public static final TextConfigKey COMMAND_KIT_NOT_FOUND = textKey("commands.kit.not_found");

    public static final TextConfigKey COMMAND_KITS_OPENING = textKey("commands.kits.opening");

    public static final TextConfigKey COMMAND_DELETEKIT_USAGE = textKey("commands.deletekit.usage");
    public static final TextConfigKey COMMAND_DELETEKIT_NOT_FOUND = textKey("commands.deletekit.not_found");
    public static final TextConfigKey COMMAND_DELETEKIT_DELETED = textKey("commands.deletekit.deleted");

    public static final TextConfigKey TAB_HEADER = textKey("tab.header");
    public static final TextConfigKey TAB_FOOTER_NO_TEAM = textKey("tab.footer_no_team");
    public static final TextConfigKey TAB_FOOTER_TEAM = textKey("tab.footer_team");

    public static final TextConfigKey SHIELD_PLACE = textKey("shield.place");
    public static final TextConfigKey SHIELD_CHANGE_RADIUS = textKey("shield.change_radius");
    public static final TextConfigKey SHIELD_LOCKED = textKey("shield.locked");

    public static final TextConfigKey COMMAND_MESSAGE_USAGE = textKey("commands.message.usage");
    public static final TextConfigKey COMMAND_MESSAGE_SELF = textKey("commands.message.self");
    public static final TextConfigKey COMMAND_MESSAGE_FORMAT_RECEIVER = textKey("commands.message.format_receiver");
    public static final TextConfigKey COMMAND_MESSAGE_FORMAT_SENDER = textKey("commands.message.format_sender");

    public static final TextConfigKey COMMAND_SOCIAL_SPY_TOGGLE_ON = textKey("commands.social_spy.toggle_on");
    public static final TextConfigKey COMMAND_SOCIAL_SPY_TOGGLE_OFF = textKey("commands.social_spy.toggle_off");
    public static final TextConfigKey COMMAND_SOCIAL_SPY_FORMAT = textKey("commands.social_spy.format");

    public static final TextConfigKey COMMAND_REPLY_USAGE = textKey("commands.reply.usage");
    public static final TextConfigKey COMMAND_REPLY_NOT_ONLINE = textKey("commands.reply.not_online");
    public static final TextConfigKey COMMAND_REPLY_NO_RECENT = textKey("commands.reply.no_recent");

    public static final TextConfigKey COMMAND_SETHOME_USAGE = textKey("commands.sethome.usage");
    public static final TextConfigKey COMMAND_SETHOME_TOO_MANY = textKey("commands.sethome.too_many");
    public static final TextConfigKey COMMAND_SETHOME_IN_USE = textKey("commands.sethome.in_use");
    public static final TextConfigKey COMMAND_SETHOME_INVALID_LOCATION = textKey("commands.sethome.invalid_location");
    public static final TextConfigKey COMMAND_SETHOME_SET = textKey("commands.sethome.set");

    public static final TextConfigKey COMMAND_HOME_AVAILABLE = textKey("commands.home.available");
    public static final TextConfigKey COMMAND_HOME_NO_HOMES = textKey("commands.home.no_homes");
    public static final TextConfigKey COMMAND_HOME_NOT_FOUND = textKey("commands.home.not_found");
    public static final TextConfigKey COMMAND_HOME_TELEPORTING = textKey("commands.home.teleporting");

    public static final TextConfigKey COMMAND_DELHOME_USAGE = textKey("commands.delhome.usage");
    public static final TextConfigKey COMMAND_DELHOME_NOT_FOUND = textKey("commands.delhome.not_found");
    public static final TextConfigKey COMMAND_DELHOME_DELETED = textKey("commands.delhome.deleted");

    public static final TextConfigKey COMMAND_ENCHANT_USAGE = textKey("commands.enchant.usage");
    public static final TextConfigKey COMMAND_ENCHANT_NO_ITEM = textKey("commands.enchant.no_item");
    public static final TextConfigKey COMMAND_ENCHANT_INVALID_ENCHANT = textKey("commands.enchant.invalid_enchant");
    public static final TextConfigKey COMMAND_ENCHANT_INVALID_RANGE = textKey("commands.enchant.invalid_range");
    public static final TextConfigKey COMMAND_ENCHANT_ENCHANTED = textKey("commands.enchant.enchanted");

    public static final String PLUGIN_RESTARTING = ChatColor.YELLOW + "Server Restarting...";
    public static final String PLUGIN_INVALID_STATE = ChatColor.RED + "&cInvalid state. Please contact a server administrator.";

    public static final TextConfigKey DEATH_KILL = textKey("deaths.kill");
    public static final TextConfigKey DEATH_WORLD_BORDER = textKey("deaths.world_border");
    public static final TextConfigKey DEATH_CONTACT = textKey("deaths.contact");
    public static final TextConfigKey DEATH_ENTITY_ATTACK = textKey("deaths.entity_attack");
    public static final TextConfigKey DEATH_ENTITY_SWEEP_ATTACK = textKey("deaths.entity_sweep_attack");
    public static final TextConfigKey DEATH_PROJECTILE = textKey("deaths.projectile");
    public static final TextConfigKey DEATH_SUFFOCATION = textKey("deaths.suffocation");
    public static final TextConfigKey DEATH_FALL = textKey("deaths.fall");
    public static final TextConfigKey DEATH_FIRE = textKey("deaths.fire");
    public static final TextConfigKey DEATH_MELTING = textKey("deaths.melting");
    public static final TextConfigKey DEATH_LAVA = textKey("deaths.lava");
    public static final TextConfigKey DEATH_MAGIC = textKey("deaths.magic");
    public static final TextConfigKey DEATH_DROWNED = textKey("deaths.drowned");
    public static final TextConfigKey DEATH_BLOCK_EXPLOSION = textKey("deaths.block_explosion");
    public static final TextConfigKey DEATH_VOID = textKey("deaths.void");
    public static final TextConfigKey DEATH_LIGHTNING = textKey("deaths.lightning");
    public static final TextConfigKey DEATH_SUICIDE = textKey("deaths.suicide");
    public static final TextConfigKey DEATH_STARVATION = textKey("deaths.starvation");
    public static final TextConfigKey DEATH_POISON = textKey("deaths.poison");
    public static final TextConfigKey DEATH_WITHER = textKey("deaths.wither");
    public static final TextConfigKey DEATH_FALLING_BLOCK = textKey("deaths.falling_block");
    public static final TextConfigKey DEATH_THORNS = textKey("deaths.thorns");
    public static final TextConfigKey DEATH_DRAGON_BREATH = textKey("deaths.dragon_breath");
    public static final TextConfigKey DEATH_FLY_INTO_WALL = textKey("deaths.fly_into_wall");
    public static final TextConfigKey DEATH_HOT_FLOOR = textKey("deaths.hot_floor");
    public static final TextConfigKey DEATH_CRAMMING = textKey("deaths.cramming");
    public static final TextConfigKey DEATH_DRYOUT = textKey("deaths.dryout");
    public static final TextConfigKey DEATH_FREEZE = textKey("deaths.freeze");
    public static final TextConfigKey DEATH_SONIC_BOOM = textKey("deaths.sonic_boom");
}
