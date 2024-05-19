package dev.igoyek.eye.command

import com.jagrosh.jdautilities.command.SlashCommand
import com.jagrosh.jdautilities.command.SlashCommandEvent
import dev.igoyek.eye.config.AppConfig
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.interactions.commands.build.OptionData
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import java.awt.Color
import java.time.Instant
import java.util.concurrent.TimeUnit

class BanCommand(private val appConfig: AppConfig) : SlashCommand() {

    init {
        this.name = "ban"
        this.help = "Ban a user"
        this.userPermissions = arrayOf(Permission.BAN_MEMBERS)
        this.options = listOf(
            OptionData(OptionType.USER, "user", "user", true),
            OptionData(OptionType.INTEGER, "deletion-time", "deletion-time", true)
                .addChoice("Don't delete", 0)
                .addChoice("1 hour", 1)
                .addChoice("6 hours", 6)
                .addChoice("12 hours", 12)
                .addChoice("1 day", 24)
                .addChoice("3 days", 72)
                .addChoice("1 week", 168),
            OptionData(OptionType.STRING, "reason", "reason", false)
        )
    }

    override fun execute(event: SlashCommandEvent) {
        try {
            val user: User = event.getOption("user")?.asUser ?: return
            val deletionTime: Int = event.getOption("deletion-time")?.asLong?.toInt() ?: 0
            val reason: String = event.getOption("reason")?.asString ?: ""
            val kickReason = "Reason: ${reason.ifEmpty { "No reason provided" }}"

            if (user.isBot) {
                event.reply("You can't ban a bot")
                    .setEphemeral(true)
                    .queue()
                return
            }

            user.openPrivateChannel().queue { privateChannel ->
                val embed: MessageEmbed = EmbedBuilder()
                    .setTitle("You have been banned from ${event.guild?.name}")
                    .setColor(Color.decode(appConfig.embedSettings.errorColor))
                    .setDescription(kickReason)
                    .setTimestamp(Instant.now())
                    .build()

                privateChannel.sendMessageEmbeds(embed).submit()
            }

            val embed: MessageEmbed = EmbedBuilder()
                .setTitle("Successfully banned ${user.name}")
                .setColor(Color.decode(appConfig.embedSettings.successColor))
                .setThumbnail(user.avatarUrl)
                .setDescription(kickReason)
                .setFooter("Requested by ${event.user.name}", event.user.avatarUrl)
                .setTimestamp(Instant.now())
                .build()

            event.replyEmbeds(embed)
                .setEphemeral(true)
                .queue()

            event.guild?.ban(user, deletionTime, TimeUnit.HOURS)
                ?.reason(reason)
                ?.queue()
        } catch (exception: Exception) {
            event.reply("Failed to ban user, they probably have a higher role than me!")
                .setEphemeral(true)
                .queue()
        }
    }
}