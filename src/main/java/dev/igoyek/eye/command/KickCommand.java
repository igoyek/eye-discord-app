package dev.igoyek.eye.command;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import dev.igoyek.eye.config.AppConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class KickCommand extends SlashCommand {

    private final AppConfig appConfig;

    public KickCommand(AppConfig appConfig) {
        this.appConfig = appConfig;

        this.name = "kick";
        this.help = "Kick a user";

        this.userPermissions = new Permission[]{Permission.KICK_MEMBERS};

        this.options = List.of(
                new OptionData(OptionType.USER, "user", "user", true),
                new OptionData(OptionType.STRING, "reason", "reason", false)
        );
    }

    @Override
    public void execute(SlashCommandEvent event) {
        try {
            User user = event.getOption("user").getAsUser();
            String reason = event.getOption("reason").getAsString();

            if (user.isBot()) {
                event.reply("You can't ban a bot")
                        .setEphemeral(true)
                        .queue();
                return;
            }

            user.openPrivateChannel().queue(privateChannel -> {
                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("You have been kicked from " + event.getGuild().getName())
                        .setColor(Color.decode(this.appConfig.embedSettings.errorColor))
                        .setDescription("Reason: " + reason)
                        .setTimestamp(Instant.now())
                        .build();

                privateChannel.sendMessageEmbeds(embed).submit();
            });

            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("Successfully kicked " + user.getName())
                    .setColor(Color.decode(this.appConfig.embedSettings.successColor))
                    .setThumbnail(user.getAvatarUrl())
                    .setDescription("Reason: " + reason)
                    .setFooter("Requested by " + event.getUser().getName(), event.getUser().getAvatarUrl())
                    .setTimestamp(Instant.now())
                    .build();

            event.replyEmbeds(embed)
                    .setEphemeral(true)
                    .queue();

            event.getGuild().kick(user, reason).queue();
        } catch (Exception exception) {
            event.reply("Failed to kick user, he probably has highest role than me!")
                    .setEphemeral(true)
                    .queue();
        }
    }
}
