package dev.igoyek.eye.command

import com.jagrosh.jdautilities.command.SlashCommand
import com.jagrosh.jdautilities.command.SlashCommandEvent
import dev.igoyek.eye.config.AppConfig
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color
import java.time.Instant

class PingCommand(private val appConfig: AppConfig) : SlashCommand() {

    init {
        this.name = "ping"
        this.help = "Check the bot's latency"
    }

    override fun execute(event: SlashCommandEvent) {
        val gatewayPing: Long = event.jda.gatewayPing
        val restPing: Long = event.jda.restPing.complete()

        val embed = EmbedBuilder()
            .setTitle("Pong!")
            .setColor(Color.decode(appConfig.embedSettings.successColor))
            .addField("Gateway Ping", "$gatewayPing ms", true)
            .addField("REST Ping", "$restPing ms", true)
            .setFooter("Requested by ${event.user.name}", event.user.avatarUrl)
            .setTimestamp(Instant.now())
            .build()

        event.replyEmbeds(embed)
            .setEphemeral(true)
            .queue()
    }
}