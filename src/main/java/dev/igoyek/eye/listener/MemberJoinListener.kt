package dev.igoyek.eye.listener

import dev.igoyek.eye.config.AppConfig
import dev.igoyek.eye.util.TagFormatter
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Role
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import java.awt.Color
import java.time.Instant

class MemberJoinListener(private val appConfig: AppConfig) : ListenerAdapter() {

    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        if (event.member.user.isBot) return

        val embed = EmbedBuilder()
            .setTitle("New member arrived!")
            .setColor(Color.decode(this.appConfig.embedSettings.successColor))
            .setDescription("Welcome " + event.member.asMention + " to the server! :tada:\nSay hello to others on " + TagFormatter.channelTag(this.appConfig.channelSettings.chatChannelId))
            .setThumbnail(event.member.user.avatarUrl)
            .setFooter("User: " + event.member.user.name + " (" + event.member.id + ")", event.member.avatarUrl)
            .setTimestamp(Instant.now())
            .build();

        val memberRole: Role? = event.guild.getRoleById(appConfig.roleSettings.memberRoleId)
        memberRole?.let {
            event.guild.addRoleToMember(event.member, it).queue()
        }

        event.guild.getTextChannelById(appConfig.channelSettings.welcomeChannelId)
            ?.sendMessageEmbeds(embed)
            ?.queue()
    }
}