package dev.igoyek.eye.command

import com.jagrosh.jdautilities.command.SlashCommand
import com.jagrosh.jdautilities.command.SlashCommandEvent
import dev.igoyek.eye.config.AppConfig
import net.dv8tion.jda.api.EmbedBuilder
import java.awt.Color
import java.time.Instant

class ServerInfoCommand(private val appConfig: AppConfig) : SlashCommand() {

    init {
        this.name = "serverinfo"
        this.help = "Get information about the server"
    }

    override fun execute(event: SlashCommandEvent) {
        val guild = event.guild
        val owner = guild?.owner?.user?.name ?: "Unknown"
        val id = guild?.id
        val members = guild?.memberCount.toString()
        val roles = guild?.roles?.size.toString()
        val channels = guild?.channels?.size.toString()
        val createdAt = guild?.timeCreated.toString()

        val embed = EmbedBuilder()
            .setTitle("${guild?.name}'s information")
            .setColor(Color.decode(appConfig.embedSettings.successColor))
            .setThumbnail(guild?.iconUrl)
            .addField("ID", id.toString(), false)
            .addField("Owner", owner, false)
            .addField("Members", members, true)
            .addField("Roles", roles, true)
            .addField("Channels", channels, true)
            .addField("Created at", createdAt, false)
            .setFooter("Requested by ${event.user.name}", event.user.avatarUrl)
            .setTimestamp(Instant.now())
            .build()

        event.replyEmbeds(embed)
            .setEphemeral(true)
            .queue()
    }
}