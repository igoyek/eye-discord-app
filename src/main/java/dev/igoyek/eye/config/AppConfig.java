package dev.igoyek.eye.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;

import java.util.HashMap;
import java.util.Map;

public class AppConfig extends OkaeriConfig {

    @Comment("# The token of the bot")
    public String token = System.getenv("APPLICATION_TOKEN") != null ? String.valueOf(System.getenv("APPLICATION_TOKEN")) : "PASTE_TOKEN_HERE";

    @Comment("# The bot owner's ID")
    public long topOwnerId = System.getenv("APPLICATION_OWNER") != null ? Long.parseLong(System.getenv("APPLICATION_OWNER")) : 724637255834009672L;

    @Comment("# The ID of guild")
    public long guildId = System.getenv("APPLICATION_GUILD") != null ? Long.parseLong(System.getenv("APPLICATION_GUILD")) : 846401340308717568L;

    @Comment("# The settings of the embeds")
    public EmbedSettings embedSettings = new EmbedSettings();

    @Comment("# The settings of the channels")
    public ChannelSettings channelSettings = new ChannelSettings();

    @Comment("# The settings of roles")
    public RoleSettings roleSettings = new RoleSettings();

    @Comment("# The settings of the voice channel statistics")
    public VoiceChannelStatistics voiceChannelStatistics = new VoiceChannelStatistics();


    public static class EmbedSettings extends OkaeriConfig {
        @Comment("# The success color of the embed")
        public String successColor = "#86ec71";

        @Comment("# The warning color of the embed")
        public String warningColor = "#f7c871";

        @Comment("# The error color of the embed")
        public String errorColor = "#ec7171";

        @Comment("# The server boost color of the embed")
        public String boostColor = "#f8aaff";
    }

    public static class ChannelSettings extends OkaeriConfig {
        @Comment("# The channel ID of the welcome channel")
        public long welcomeChannelId = 1238570271158308904L;

        @Comment("# The channel ID of the audit logs channel")
        public long auditLogsChannelId = 1238984284057112667L;

        @Comment("# ID of default channel to say hello")
        public long chatChannelId = 1098594873571020800L;
    }

    public static class RoleSettings extends OkaeriConfig {
        @Comment("# The role ID of the member role")
        public long memberRoleId = 1143811613682585750L;
    }

    public static class VoiceChannelStatistics extends OkaeriConfig {
        @Comment({
                "# Placeholders:",
                "# {MEMBERS_SIZE} - the number of members",
                "# {ONLINE_MEMBERS_SIZE} - the number of online members",
                "# {BOT_MEMBERS_SIZE} - the number of bot members",
                "# {CHANNELS_SIZE} - the number of channels",
                "# {ROLES_SIZE} - the number of roles",
                "# {TEXT_CHANNELS_SIZE} - the number of text channels",
                "# {VOICE_CHANNELS_SIZE} - the number of voice channels",
                "# {CATEGORIES_SIZE} - the number of categories",
                "# {EMOJIS_SIZE} - the number of emojis",
                "# {BOOSTS_SIZE} - the number of boosts",
                "# {BOOST_TIER} - the boost tier",
        })
        public Map<Long, String> channelNames = new HashMap<>(Map.of(
                1238990644911931474L, "Members: {MEMBERS_SIZE}",
                1238990663874510871L, "Channels: {CHANNELS_SIZE}",
                1238990679623864320L, "Roles: {ROLES_SIZE}",
                1238990694966759426L, "Text Channels: {TEXT_CHANNELS_SIZE}",
                1238990711097917440L, "Voice Channels: {VOICE_CHANNELS_SIZE}",
                1238990730999890022L, "Emojis: {EMOJIS_SIZE}",
                1238990747307475016L, "Boosts: {BOOSTS_SIZE}",
                1238990769810051143L, "Boost Tier: {BOOST_TIER}",
                1238990789669949440L, "Online Users: {ONLINE_MEMBERS_SIZE}"
        ));
    }
}
