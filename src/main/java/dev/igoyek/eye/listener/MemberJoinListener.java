package dev.igoyek.eye.listener;

import dev.igoyek.eye.config.AppConfig;
import dev.igoyek.eye.util.TagFormatter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.Color;
import java.time.Instant;

public class MemberJoinListener extends ListenerAdapter {

    private final AppConfig appConfig;

    public MemberJoinListener(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if (event.getMember().getUser().isBot()) return;

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("New member arrived!")
                .setColor(Color.decode(this.appConfig.embedSettings.successColor))
                .setDescription("Welcome " + event.getMember().getAsMention() + " to the server! :tada:\nSay hello to others on " + TagFormatter.channelTag(this.appConfig.channelSettings.chatChannelId))
                .setThumbnail(event.getMember().getUser().getAvatarUrl())
                .setFooter("User: " + event.getMember().getUser().getName() + " (" + event.getMember().getId() + ")", event.getMember().getAvatarUrl())
                .setTimestamp(Instant.now())
                .build();

        Role memberRole = event.getGuild().getRoleById(this.appConfig.roleSettings.memberRoleId);
        event.getMember().getGuild().addRoleToMember(event.getMember(), memberRole).queue();

        event.getGuild().getTextChannelById(this.appConfig.channelSettings.welcomeChannelId).sendMessageEmbeds(embed).queue();
    }
}
