package dev.igoyek.eye.logs;

import dev.igoyek.eye.config.AppConfig;
import dev.igoyek.eye.util.UserUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;

public class MemberLeft extends ListenerAdapter {

    private final AppConfig appConfig;

    public MemberLeft(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public void onMemberLeft(GuildMemberRemoveEvent event) {
        User user = event.getUser();
        Guild guild = event.getGuild();

        if(UserUtil.isUserBanned(user)) return;

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Member left")
                .setDescription(user.getAsMention() + " has left the server")
                .setThumbnail(user.getEffectiveAvatarUrl())
                .setFooter("User: " + user.getName() + "(" + user.getId() + ")", user.getAvatarUrl())
                .setTimestamp(Instant.now())
                .build();

        TextChannel loggingChannel = guild.getTextChannelById(this.appConfig.channelSettings.auditLogsChannelId);
        loggingChannel.sendMessageEmbeds(embed).queue();
    }
}
