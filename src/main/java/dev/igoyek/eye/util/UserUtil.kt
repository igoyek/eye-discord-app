package dev.igoyek.eye.util

import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.User

class UserUtil {

    companion object {

        @JvmStatic
        fun isUserBanned(user: User, guild: Guild): Boolean {
            return guild.retrieveBanList().complete().any() { it.user.id == user.id }
        }
    }
}