package dev.igoyek.eye.util;

import dev.igoyek.eye.config.AppConfig;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

public final class UserUtil {

    private static AppConfig appConfig;

    public static boolean isUserBanned(User user) {
        Guild guild = user.getJDA().getGuildById(appConfig.guildId);

        if (guild == null) return false;

        return guild.retrieveBanList().complete().stream().anyMatch(ban -> ban.getUser().getId().equals(user.getId()));
    }
}
