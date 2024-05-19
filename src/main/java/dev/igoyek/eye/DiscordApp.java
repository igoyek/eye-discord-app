package dev.igoyek.eye;

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import dev.igoyek.eye.event.MemberJoinListener;
import dev.igoyek.eye.command.AvatarCommand;
import dev.igoyek.eye.command.BanCommand;
import dev.igoyek.eye.command.BotInfoCommand;
import dev.igoyek.eye.command.ClearCommand;
import dev.igoyek.eye.command.CooldownCommand;
import dev.igoyek.eye.command.EmbedCommand;
import dev.igoyek.eye.command.KickCommand;
import dev.igoyek.eye.command.PingCommand;
import dev.igoyek.eye.command.SayCommand;
import dev.igoyek.eye.command.ServerInfoCommand;
import dev.igoyek.eye.config.AppConfig;
import dev.igoyek.eye.config.ConfigFactory;
import dev.igoyek.eye.config.DatabaseConfig;
import dev.igoyek.eye.database.DatabaseManager;
import dev.igoyek.eye.guildstats.GuildStatisticsService;
import dev.igoyek.eye.guildstats.GuildStatisticsTask;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.SQLException;
import java.time.Duration;
import java.util.EnumSet;
import java.util.Timer;

public class DiscordApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiscordApp.class);

    public static void main(String... args) throws InterruptedException {
        ConfigFactory configFactory = new ConfigFactory();

        AppConfig appConfig = configFactory.produceConfig(AppConfig.class, new File("config.yml"));
        DatabaseConfig databaseConfig = configFactory.produceConfig(DatabaseConfig.class, new File("database.yml"));

        try {
            DatabaseManager databaseManager = new DatabaseManager(databaseConfig, new File("database"));
            databaseManager.connect();

        } catch (SQLException exception) {
            LOGGER.error("Failed to connect to database", exception);
        }

        CommandClient commandClient = new CommandClientBuilder()
                .setOwnerId(appConfig.topOwnerId)
                .setActivity(Activity.watching("igoyek.dev"))
                .setStatus(OnlineStatus.DO_NOT_DISTURB)
                .useHelpBuilder(false)

                .addSlashCommands(
                        new AvatarCommand(appConfig),
                        new BanCommand(appConfig),
                        new BotInfoCommand(appConfig),
                        new ClearCommand(appConfig),
                        new CooldownCommand(appConfig),
                        new EmbedCommand(appConfig),
                        new KickCommand(appConfig),
                        new PingCommand(appConfig),
                        new SayCommand(appConfig),
                        new ServerInfoCommand(appConfig)
                )
                .build();

        JDA jda = JDABuilder.createDefault(appConfig.token)
                .addEventListeners(
                        commandClient,

                        // welcome messages
                        new MemberJoinListener(appConfig)
                )

                .setAutoReconnect(true)

                .enableIntents(EnumSet.allOf(GatewayIntent.class))
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableCache(CacheFlag.ONLINE_STATUS)
                .setChunkingFilter(ChunkingFilter.ALL)

                .build()
                .awaitReady();

        GuildStatisticsService guildStatisticsService = new GuildStatisticsService(appConfig, jda);

        Timer timer = new Timer();
        timer.schedule(new GuildStatisticsTask(guildStatisticsService), 0, Duration.ofMinutes(5L).toMillis());
    }

}
