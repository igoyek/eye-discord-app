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

class ClearCommand(private val appConfig: AppConfig) : SlashCommand() {

    init {
        this.name = "clear"
        this.help = "Clears the specified amount of messages"
        this.userPermissions = arrayOf(Permission.MESSAGE_MANAGE)
        this.options = listOf(
            OptionData(OptionType.INTEGER, "amount", "amount", true)
                .setMinValue(1)
                .setMaxValue(100)
        )
    }

    override fun execute(event: SlashCommandEvent) {
        val amount: Int = event.getOption("amount")?.asLong?.toInt() ?: return

        event.channel.iterableHistory.takeAsync(amount)
            .thenAcceptAsync { messages -> event.channel.purgeMessages(messages) }

        val messageEmbed = EmbedBuilder()
            .setTitle("Messages cleared")
            .setColor(Color.decode(appConfig.embedSettings.successColor))
            .setDescription("Cleared $amount messages")
            .setFooter("Requested by ${event.user.name}", event.user.avatarUrl)
            .setTimestamp(Instant.now())
            .build()

        event.replyEmbeds(messageEmbed)
            .setEphemeral(true)
            .queue()
    }
}