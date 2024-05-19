package dev.igoyek.eye.command;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import dev.igoyek.eye.config.AppConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;

public class PingCommand extends SlashCommand {

    private final AppConfig appConfig;

    public PingCommand(AppConfig appConfig) {
        this.appConfig = appConfig;

        this.name = "ping";
        this.help = "Check the bot's latency";
    }

    @Override
    public void execute(SlashCommandEvent event) {
        long gatewayPing = event.getJDA().getGatewayPing();
        long restPing = event.getJDA().getRestPing().complete();

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Pong!")
                .setColor(Color.decode(this.appConfig.embedSettings.successColor))
                .addField("Gateway Ping", String.format("%dms", gatewayPing), true)
                .addField("REST Ping", String.format("%dms", restPing), true)
                .setFooter("Requested by" + event.getUser().getName(), event.getUser().getAvatarUrl())
                .setTimestamp(Instant.now())
                .build();

        event.replyEmbeds(embed)
                .setEphemeral(true)
                .queue();
    }
}
