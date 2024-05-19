package dev.igoyek.eye.command;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import dev.igoyek.eye.config.AppConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.time.Instant;
import java.util.List;

public class CooldownCommand extends SlashCommand {

    private final AppConfig appConfig;

    public CooldownCommand(AppConfig appConfig) {
        this.appConfig = appConfig;

        this.name = "cooldown";
        this.help = "Set the cooldown of a chat channel";

        this.userPermissions = new Permission[]{Permission.MANAGE_CHANNEL};

        this.options = List.of(
                new OptionData(OptionType.INTEGER, "cooldown", "cooldown", true)
                        .setMinValue(0)
                        .setMaxValue(21600)
        );
    }

    @Override
    public void execute(SlashCommandEvent event) {
        int cooldown = event.getOption("cooldown").getAsInt();

        event.getChannel().asTextChannel().getManager().setSlowmode(cooldown).queue();

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("Cooldown set")
                .setColor(Color.decode(this.appConfig.embedSettings.successColor))
                .setDescription("This channel's cooldown is now " + cooldown + " seconds")
                .setFooter("Requested by " + event.getUser().getName(), event.getUser().getAvatarUrl())
                .setTimestamp(Instant.now())
                .build();

        event.replyEmbeds(embed)
                .setEphemeral(true)
                .queue();
    }
}
