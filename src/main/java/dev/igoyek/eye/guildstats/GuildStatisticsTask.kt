package dev.igoyek.eye.guildstats

import java.util.TimerTask


class GuildStatisticsTask (private val guildStatisticsService: GuildStatisticsService) : TimerTask() {
    override fun run() {
        guildStatisticsService.displayStats()
    }
}