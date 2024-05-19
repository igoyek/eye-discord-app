package dev.igoyek.eye.command;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import dev.igoyek.eye.config.AppConfig;
import dev.igoyek.eye.util.TagFormatter;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;

public class ServerInfoCommand extends SlashCommand {

    private final AppConfig appConfig;

    public ServerInfoCommand(AppConfig appConfig) {
        this.appConfig = appConfig;

        this.name = "serverinfo";
        this.help = "Get information about the server";
    }

    @Override
    public void execute(SlashCommandEvent event) {
        Guild guild = event.getGuild();
        String owner = TagFormatter.memberTag(guild.getOwner().getUser());
        String id = guild.getId();
        String members = String.valueOf(guild.getMemberCount());
        String roles = String.valueOf(guild.getRoles().size());
        String channels = String.valueOf(guild.getChannels().size());
        String createdAt = TagFormatter.timestampTag(guild.getTimeCreated());

        MessageEmbed embed = new EmbedBuilder()
                .setTitle(guild.getName() + "'s information")
                .setColor(Color.decode(this.appConfig.embedSettings.successColor))
                .setThumbnail(guild.getIconUrl())
                .addField("ID", id, false)
                .addField("Owner", owner, false)
                .addField("Members", members, true)
                .addField("Roles", roles, true)
                .addField("Channels", channels, true)
                .addField("Created at", createdAt, false)
                .setFooter("Requested by " + event.getUser().getName(), event.getUser().getAvatarUrl())
                .setTimestamp(Instant.now())
                .build();

        event.replyEmbeds(embed)
                .setEphemeral(true)
                .queue();
    }
}
