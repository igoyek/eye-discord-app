package dev.igoyek.eye

import com.jagrosh.jdautilities.command.CommandClient
import com.jagrosh.jdautilities.command.CommandClientBuilder
import dev.igoyek.eye.command.AvatarCommand
import dev.igoyek.eye.command.BanCommand
import dev.igoyek.eye.command.BotInfoCommand
import dev.igoyek.eye.command.ClearCommand
import dev.igoyek.eye.command.CooldownCommand
import dev.igoyek.eye.command.EmbedCommand
import dev.igoyek.eye.command.KickCommand
import dev.igoyek.eye.command.PingCommand
import dev.igoyek.eye.command.SayCommand
import dev.igoyek.eye.command.ServerInfoCommand
import dev.igoyek.eye.config.AppConfig
import dev.igoyek.eye.config.ConfigFactory
import dev.igoyek.eye.guildstats.GuildStatisticsService
import dev.igoyek.eye.guildstats.GuildStatisticsTask
import dev.igoyek.eye.listener.MemberJoinListener
import dev.igoyek.eye.listener.MemberLeftListener
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.OnlineStatus
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.ChunkingFilter
import net.dv8tion.jda.api.utils.MemberCachePolicy
import net.dv8tion.jda.api.utils.cache.CacheFlag
import java.io.File
import java.time.Duration
import java.util.EnumSet
import java.util.Timer

class DiscordApp {
    fun run() {
        val configFactory = ConfigFactory()
        val appConfig = configFactory.produceConfig(AppConfig::class.java, File("config.yml"))

        val commandClient: CommandClient = CommandClientBuilder()
            .setOwnerId(appConfig.topOwnerId)
            .setActivity(Activity.watching("igoyek.dev"))
            .setStatus(OnlineStatus.DO_NOT_DISTURB)
            .useHelpBuilder(false)

            .addSlashCommands(
                AvatarCommand(appConfig),
                BanCommand(appConfig),
                BotInfoCommand(appConfig),
                ClearCommand(appConfig),
                CooldownCommand(appConfig),
                EmbedCommand(appConfig),
                KickCommand(appConfig),
                PingCommand(appConfig),
                SayCommand(appConfig),
                ServerInfoCommand(appConfig)
            )
            .build();

        val jda = JDABuilder.createDefault(appConfig.token)
            .addEventListeners(
                commandClient,

                // listeners
                MemberJoinListener(appConfig),
                MemberLeftListener(appConfig)
            )

            .setAutoReconnect(true)

            .enableIntents(EnumSet.allOf(GatewayIntent::class.java))
            .setMemberCachePolicy(MemberCachePolicy.ALL)
            .enableCache(CacheFlag.ONLINE_STATUS)
            .setChunkingFilter(ChunkingFilter.ALL)

            .build()
            .awaitReady();

        val guildStatisticsService = GuildStatisticsService(appConfig, jda)

        val timer = Timer()
        timer.schedule(GuildStatisticsTask(guildStatisticsService), 0, Duration.ofMinutes(5L).toMillis())
    }
}