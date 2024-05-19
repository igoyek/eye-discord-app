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

class EmbedCommand(private val appConfig: AppConfig) : SlashCommand() {

    init {
        this.name = "embed"
        this.help = "Send an embed message"
        this.userPermissions = arrayOf(Permission.ADMINISTRATOR)
        this.options = listOf(
            OptionData(OptionType.STRING, "title", "The title of the embed").setRequired(true),
            OptionData(OptionType.STRING, "description", "The description of the embed").setRequired(true),
            OptionData(OptionType.STRING, "color", "The color of the embed").setRequired(false),
            OptionData(OptionType.STRING, "footer", "The footer of the embed").setRequired(false),
            OptionData(OptionType.STRING, "author", "The thumbnail of the embed").setRequired(false),
            OptionData(OptionType.STRING, "image", "The thumbnail of the embed").setRequired(false),
            OptionData(OptionType.STRING, "thumbnail", "The thumbnail of the embed").setRequired(false),
            OptionData(OptionType.BOOLEAN, "timestamp", "Do you want to set timestamp in the embed?").setRequired(false),
            OptionData(OptionType.BOOLEAN, "ephemeral", "Do you want to set ephemeral in the embed?").setRequired(false)
        )
    }

    override fun execute(event: SlashCommandEvent) {
        val title: String = event.getOption("title")?.asString ?: return
        val description: String = event.getOption("description")?.asString ?: return
        val color: String = event.getOption("color")?.asString ?: "#000000"
        val footer: String? = event.getOption("footer")?.asString
        val author: String? = event.getOption("author")?.asString
        val image: String? = event.getOption("image")?.asString
        val thumbnail: String? = event.getOption("thumbnail")?.asString
        val timestamp: Boolean = event.getOption("timestamp")?.asBoolean ?: false
        val ephemeral: Boolean = event.getOption("ephemeral")?.asBoolean ?: false

        val embed = EmbedBuilder()
            .setAuthor(author)
            .setTitle(title)
            .setColor(Color.decode(color))
            .setThumbnail(thumbnail)
            .setDescription(description)
            .setImage(image)
            .setFooter(footer)
            .setTimestamp(if (timestamp) Instant.now() else null)
            .build()

        event.replyEmbeds(embed)
            .setEphemeral(ephemeral)
            .queue()
    }
}