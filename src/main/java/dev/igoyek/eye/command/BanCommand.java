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

public class BanCommand extends SlashCommand {

    private final AppConfig appConfig;

    public BanCommand(AppConfig appConfig) {
        this.appConfig = appConfig;

        this.name = "ban";
        this.help = "Ban a user";

        this.userPermissions = new Permission[]{Permission.BAN_MEMBERS};

        this.options = List.of(
                new OptionData(OptionType.USER, "user", "user", true),
                new OptionData(OptionType.INTEGER, "deletion-time", "deletion-time", true)
                        .addChoice("Don't delete", 0)
                        .addChoice("1 hour", 1)
                        .addChoice("6 hours", 6)
                        .addChoice("12 hours", 12)
                        .addChoice("1 day", 24)
                        .addChoice("3 days", 72)
                        .addChoice("1 week", 168),
                new OptionData(OptionType.STRING, "reason", "reason", false)
        );
    }

    @Override
    public void execute(SlashCommandEvent event) {
        try {
            User user = event.getOption("user").getAsUser();
            int deletionTime = event.getOption("deletion-time").getAsInt();
            String reason = event.getOption("reason").getAsString();
            String kickReason = "Reason: " + (reason.isEmpty() ? "No reason provided" : reason);

            if (user.isBot()) {
                event.reply("You can't ban a bot")
                        .setEphemeral(true)
                        .queue();
                return;
            }

            user.openPrivateChannel().queue(privateChannel -> {
                MessageEmbed embed = new EmbedBuilder()
                        .setTitle("You have been banned from " + event.getGuild().getName())
                        .setColor(Color.decode(this.appConfig.embedSettings.errorColor))
                        .setDescription(kickReason)
                        .setTimestamp(Instant.now())
                        .build();

                privateChannel.sendMessageEmbeds(embed).submit();
            });

            MessageEmbed embed = new EmbedBuilder()
                    .setTitle("Successfully banned " + user.getName())
                    .setColor(Color.decode(this.appConfig.embedSettings.successColor))
                    .setThumbnail(user.getAvatarUrl())
                    .setDescription(kickReason)
                    .setFooter("Requested by " + event.getUser().getName(), event.getUser().getAvatarUrl())
                    .setTimestamp(Instant.now())
                    .build();

            event.replyEmbeds(embed)
                    .setEphemeral(true)
                    .queue();

            event.getGuild().ban(user, deletionTime, TimeUnit.HOURS)
                    .reason(reason)
                    .queue();
        } catch (Exception exception) {
            event.reply("Failed to ban user, he probably has highest role than me!")
                    .setEphemeral(true)
                    .queue();
        }
    }
}
