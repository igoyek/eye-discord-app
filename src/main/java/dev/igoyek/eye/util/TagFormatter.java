package dev.igoyek.eye.util;

import net.dv8tion.jda.api.entities.User;

import java.time.OffsetDateTime;

public final class TagFormatter {

    private static final String MEMBER_TAG = "<@%s>";
    private static final String CHANNEL_TAG = "<#%s>";
    private static final String TIMESTAMP_TAG = "<t:%d:F>";

    private TagFormatter() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static String memberTag(User user) {
        return String.format(MEMBER_TAG, user.getId());
    }

    public static String channelTag(long channelId) {
        return String.format(CHANNEL_TAG, channelId);
    }

    public static String offsetTime(OffsetDateTime offsetDateTime) {
        return String.format(TIMESTAMP_TAG, offsetDateTime.toEpochSecond());
    }
}

