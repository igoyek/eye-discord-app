package dev.igoyek.eye.command

import com.jagrosh.jdautilities.command.SlashCommand
import com.jagrosh.jdautilities.command.SlashCommandEvent
import dev.igoyek.eye.config.AppConfig
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import java.awt.Color
import java.time.Instant

class CooldownCommand(private val appConfig: AppConfig) : SlashCommand() {

    init {
        this.name = "cooldown"
        this.help = "Set the cooldown of a chat channel"
        this.userPermissions = arrayOf(Permission.MANAGE_CHANNEL)
        this.options = listOf(
            OptionData(OptionType.INTEGER, "cooldown", "cooldown", true)
                .setMinValue(0)
                .setMaxValue(21600)
        )
    }

    override fun execute(event: SlashCommandEvent) {
        val cooldown: Int = event.getOption("cooldown")?.asLong?.toInt() ?: return

        event.channel.asTextChannel().manager.setSlowmode(cooldown).queue()

        val embed = EmbedBuilder()
            .setTitle("Cooldown set")
            .setColor(Color.decode(appConfig.embedSettings.successColor))
            .setDescription("This channel's cooldown is now $cooldown seconds")
            .setFooter("Requested by ${event.user.name}", event.user.avatarUrl)
            .setTimestamp(Instant.now())
            .build()

        event.replyEmbeds(embed)
            .setEphemeral(true)
            .queue()
    }
}