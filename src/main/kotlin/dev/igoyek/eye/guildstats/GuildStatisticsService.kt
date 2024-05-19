package dev.igoyek.eye.guildstats

import dev.igoyek.eye.config.AppConfig
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel
import panda.utilities.text.Formatter

class GuildStatisticsService(private val appConfig: AppConfig, private val jda: JDA) {

    fun displayStats() {
        val guild: Guild = jda.getGuildById(appConfig.guildId) ?: return

        appConfig.voiceChannelStatistics.members.forEach() { (key, value) ->
            val channel: VoiceChannel = guild.getVoiceChannelById(key) ?: return

            val formatter = Formatter()
                .register("{MEMBERS_SIZE}", guild.memberCache.stream()
                    .filter { member -> !member.user.isBot }
                    .count()
                )

            val stats = formatter.format(value)
            channel.manager.setName(stats).queue();
        }
    }
}