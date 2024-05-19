package dev.igoyek.eye.command

import com.jagrosh.jdautilities.command.SlashCommand
import com.jagrosh.jdautilities.command.SlashCommandEvent
import dev.igoyek.eye.config.AppConfig
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData

class SayCommand(val appConfig: AppConfig) : SlashCommand() {

    init {
        this.name = "say"
        this.help = "Repeats what you say"
        this.userPermissions = arrayOf(net.dv8tion.jda.api.Permission.MESSAGE_MANAGE)
        this.options = listOf(
            OptionData(OptionType.STRING, "message", "message")
                .setRequired(true)
        )
    }

    override fun execute(event: SlashCommandEvent) {
        val message: String = event.getOption("message")?.asString ?: return

        event.channel.sendMessage(message).queue()
        event.reply("Message sent!")
            .setEphemeral(true)
            .queue()
    }
}