package dev.igoyek.eye.command

import com.jagrosh.jdautilities.command.SlashCommand
import com.jagrosh.jdautilities.command.SlashCommandEvent
import dev.igoyek.eye.config.AppConfig
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import java.awt.Color
import java.time.Instant

class AvatarCommand(private val appConfig: AppConfig) : SlashCommand() {

    init {
        this.name = "avatar"
        this.help = "Shows the avatar of a user"
        this.options = listOf(
            OptionData(OptionType.USER, "user", "Select a user").setRequired(false)
        )
    }

    override fun execute(event: SlashCommandEvent) {
        val user: User = event.getOption("user")?.asUser ?: event.user

        val embed: MessageEmbed = EmbedBuilder()
            .setTitle("${user.name}'s avatar")
            .setColor(Color.decode(appConfig.embedSettings.successColor))
            .setImage("${user.effectiveAvatarUrl}?size=2048")
            .setTimestamp(Instant.now())
            .setFooter("Requested by ${event.user.name}", event.user.avatarUrl)
            .build()

        event.replyEmbeds(embed)
            .setEphemeral(true)
            .queue()
    }
}