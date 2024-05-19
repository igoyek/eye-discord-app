package dev.igoyek.eye.event;

import dev.igoyek.eye.config.AppConfig;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ServerBoostListener extends ListenerAdapter {

    private final AppConfig appConfig;

    public ServerBoostListener(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    /*@Override
    public void onServerBoost(GuildUpdateBoostCountEvent event) {
        if (event.getOldBoostCount() > event.getNewBoostCount()) return;

        MessageEmbed embed = new EmbedBuilder()
                .setTitle("New server boost!")
                .setColor(Color.decode(this.appConfig.embedSettings.boostColor))
                .setDescription("Thank you " + TagFormat.memberTag() + " for boosting the server! :tada:")

    }*/
}
