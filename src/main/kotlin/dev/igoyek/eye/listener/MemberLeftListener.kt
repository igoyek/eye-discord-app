package dev.igoyek.eye.listener

import dev.igoyek.eye.config.AppConfig
import dev.igoyek.eye.util.UserUtil
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color
import java.time.Instant

class MemberLeftListener(private val appConfig: AppConfig) : ListenerAdapter() {

    override fun onGuildMemberRemove(event: GuildMemberRemoveEvent) {
        val user: User = event.user
        val guild: Guild = event.guild

        if (UserUtil.isUserBanned(user, guild)) {
            return
        }

        val embed = EmbedBuilder()
            .setTitle("Member left")
            .setColor(Color.decode(appConfig.embedSettings.errorColor))
            .setDescription(user.asMention + " has left the server \n Goodbye! :wave:")
            .setThumbnail(user.effectiveAvatarUrl)
            .setFooter("User: " + user.name + "(" + user.id + ")", user.avatarUrl)
            .setTimestamp(Instant.now())
            .build();

        event.guild.getTextChannelById(appConfig.channelSettings.auditLogsChannelId)
            ?.sendMessageEmbeds(embed)
            ?.queue()
    }
}