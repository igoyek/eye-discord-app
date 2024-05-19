package dev.igoyek.eye.config

import eu.okaeri.configs.OkaeriConfig
import eu.okaeri.configs.annotation.Comment

class AppConfig : OkaeriConfig() {

    @Comment("# The token of the bot")
    var token: String = System.getenv("APPLICATION_TOKEN") ?: "PASTE_TOKEN_HERE"

    @Comment("# The bot owner's ID")
    var topOwnerId: Long = System.getenv("APPLICATION_OWNER")?.toLong() ?: 724637255834009672L

    @Comment("# The ID of guild")
    var guildId: Long = System.getenv("APPLICATION_GUILD")?.toLong() ?: 846401340308717568L

    @Comment("# The settings of the embeds")
    var embedSettings: EmbedSettings = EmbedSettings()

    @Comment("# The settings of the channels")
    var channelSettings: ChannelSettings = ChannelSettings()

    @Comment("# The settings of roles")
    var roleSettings: RoleSettings = RoleSettings()

    @Comment("# The settings of the voice channel statistics")
    var voiceChannelStatistics: VoiceChannelStatistics = VoiceChannelStatistics()


    class EmbedSettings : OkaeriConfig() {
        @Comment("# The success color of the embed")
        var successColor: String = "#86ec71"

        @Comment("# The warning color of the embed")
        var warningColor: String = "#f7c871"

        @Comment("# The error color of the embed")
        var errorColor: String = "#ec7171"

        @Comment("# The server boost color of the embed")
        var boostColor: String = "#f8aaff"
    }

    class ChannelSettings : OkaeriConfig() {
        @Comment("# The channel ID of the welcome channel")
        var welcomeChannelId: Long = 1238570271158308904L

        @Comment("# The channel ID of the audit logs channel")
        var auditLogsChannelId: Long = 1238984284057112667L

        @Comment("# ID of default channel to say hello")
        var chatChannelId: Long = 1098594873571020800L
    }

    class RoleSettings : OkaeriConfig() {
        @Comment("# The role ID of the member role")
        var memberRoleId: Long = 1143811613682585750L
    }

    class VoiceChannelStatistics : OkaeriConfig() {
        @Comment("# Members statistic")
        var members: Map<Long, String> = mapOf(1238990644911931474L to "Members: {MEMBERS_SIZE}")
    }
}