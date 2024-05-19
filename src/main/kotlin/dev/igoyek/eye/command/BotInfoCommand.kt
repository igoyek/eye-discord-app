package dev.igoyek.eye.command

import com.jagrosh.jdautilities.command.SlashCommand
import com.jagrosh.jdautilities.command.SlashCommandEvent
import dev.igoyek.eye.config.AppConfig
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import java.awt.Color
import java.time.Instant

class BotInfoCommand(private val appConfig: AppConfig) : SlashCommand() {

    init {
        this.name = "botinfo"
        this.help = "Get information about the bot"
    }

    override fun execute(event: SlashCommandEvent) {
        val jda: JDA = event.jda
        val embed = EmbedBuilder()
            .setTitle("Bot information")
            .setColor(Color.decode(appConfig.embedSettings.successColor))
            .setThumbnail(jda.selfUser.avatarUrl)
            .addField("Guilds", jda.guilds.size.toString(), false)
            .addField("Users", jda.users.size.toString(), true)
            .addField("Channels", jda.textChannels.size.toString(), false)
            .addField("OS", System.getProperty("os.name"), false)
            .addField("Java", System.getProperty("java.version"), false)
            .addField("Gateway Ping", jda.gatewayPing.toString(), false)
            .addField("Rest Ping", jda.restPing.complete().toString(), false)
            .setFooter("Requested by ${event.user.name}", event.user.avatarUrl)
            .setTimestamp(Instant.now())
            .build()

        event.replyEmbeds(embed)
            .setEphemeral(true)
            .queue()
    }
}