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

class KickCommand(private val appConfig: AppConfig) : SlashCommand() {

    init {
        this.name = "kick"
        this.help = "Kick a user"
        this.userPermissions = arrayOf(Permission.KICK_MEMBERS)
        this.options = listOf(
            OptionData(OptionType.USER, "user", "user", true),
            OptionData(OptionType.STRING, "reason", "reason", false)
        )
    }

    override fun execute(event: SlashCommandEvent) {
        try {
            val user = event.getOption("user")?.asUser ?: return
            val reason: String = event.getOption("reason")?.asString ?: "No reason provided"

            if (user.isBot) {
                event.reply("You can't ban a bot")
                    .setEphemeral(true)
                    .queue()
                return
            }

            user.openPrivateChannel().queue { privateChannel ->
                val embed = EmbedBuilder()
                    .setTitle("You have been kicked from ${event.guild?.name}")
                    .setColor(Color.decode(appConfig.embedSettings.errorColor))
                    .setDescription("Reason: $reason")
                    .setTimestamp(Instant.now())
                    .build()

                privateChannel.sendMessageEmbeds(embed).submit()
            }

            val embed = EmbedBuilder()
                .setTitle("Successfully kicked ${user.name}")
                .setColor(Color.decode(appConfig.embedSettings.successColor))
                .setThumbnail(user.avatarUrl)
                .setDescription("Reason: $reason")
                .setFooter("Requested by ${event.user.name}", event.user.avatarUrl)
                .setTimestamp(Instant.now())
                .build()

            event.replyEmbeds(embed)
                .setEphemeral(true)
                .queue()

            event.guild
                ?.kick(user, reason)
                ?.queue()
        } catch (exception: Exception) {
            event.reply("Failed to kick user, they probably have a higher role than me!")
                .setEphemeral(true)
                .queue()
        }
    }
}