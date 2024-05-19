package dev.igoyek.eye.command;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import dev.igoyek.eye.config.AppConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;

public class BotInfoCommand extends SlashCommand {

    private final AppConfig appConfig;

    public BotInfoCommand(AppConfig appConfig) {
        this.appConfig = appConfig;

        this.name = "botinfo";
        this.help = "Get information about the bot";
    }

    @Override
    public void execute(SlashCommandEvent event) {
        JDA jda = event.getJDA();
        Guild guild = event.getGuild();

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Bot information")
                .setColor(Color.decode(this.appConfig.embedSettings.successColor))
                .setThumbnail(jda.getSelfUser().getAvatarUrl())
                .addField("Guilds", String.valueOf(jda.getGuilds().size()), false)
                .addField("Users", String.valueOf(jda.getUsers().size()), true)
                .addField("Channels", String.valueOf(jda.getTextChannels().size()), false)
                .addField("OS", System.getProperty("os.name"), false)
                .addField("Java", System.getProperty("java.version"), false)
                .addField("Gateway Ping", String.valueOf(jda.getGatewayPing()), false)
                .addField("Rest Ping", String.valueOf(jda.getRestPing().complete()), false)
                .setFooter("Requested by " + event.getUser().getName(), event.getUser().getAvatarUrl())
                .setTimestamp(Instant.now())
                .build();

        event.replyEmbeds(embed)
                .setEphemeral(true)
                .queue();
    }
}
