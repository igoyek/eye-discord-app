package dev.igoyek.eye.util

import dev.igoyek.eye.config.AppConfig
import net.dv8tion.jda.api.entities.User

class UserUtil {

    companion object {
        private var appConfig: AppConfig? = null

        @JvmStatic
        fun isUserBanned(user: User): Boolean {
            val guild = user.jda.getGuildById(appConfig?.guildId ?: return false) ?: return false

            return guild.retrieveBanList().complete().any() { it.user.id == user.id }
        }
    }
}