package dev.igoyek.eye.command;

import com.jagrosh.jdautilities.command.SlashCommand;
import com.jagrosh.jdautilities.command.SlashCommandEvent;
import dev.igoyek.eye.config.AppConfig;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class SayCommand extends SlashCommand {

    public final AppConfig appConfig;

    public SayCommand(AppConfig appConfig) {
        this.appConfig = appConfig;

        this.name = "say";
        this.help = "Repeats what you say";

        this.userPermissions = new Permission[]{Permission.MESSAGE_MANAGE};

        this.options = List.of(
                new OptionData(OptionType.STRING, "message", "message")
                        .setRequired(true)
        );
    }

    @Override
    public void execute(SlashCommandEvent event) {
        String message = event.getOption("message").getAsString();

        event.getChannel().sendMessage(message).queue();
        event.reply("Message sent!")
                .setEphemeral(true)
                .queue();
    }
}
