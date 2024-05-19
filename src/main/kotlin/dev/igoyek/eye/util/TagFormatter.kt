package dev.igoyek.eye.util

import net.dv8tion.jda.api.entities.User
import java.time.OffsetDateTime

class TagFormatter private constructor() {

    companion object {
        private const val MEMBER_TAG = "<@%s>"
        private const val CHANNEL_TAG = "<#%s>"
        private const val TIMESTAMP_TAG = "<t:%d:F>"

        @JvmStatic
        fun memberTag(user: User): String {
            return String.format(MEMBER_TAG, user.id)
        }

        @JvmStatic
        fun channelTag(channelId: Long): String {
            return String.format(CHANNEL_TAG, channelId)
        }

        @JvmStatic
        fun timestampTag(offsetDateTime: OffsetDateTime): String {
            return String.format(TIMESTAMP_TAG, offsetDateTime.toEpochSecond())
        }
    }
}