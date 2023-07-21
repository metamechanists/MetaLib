package org.metamechanists.metalib.utils;

import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.Message;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import github.scarsz.discordsrv.util.DiscordUtil;

@SuppressWarnings("unused")
public class DiscordUtils {
    public static final String ANNOUNCEMENTS_CHANNEL = "announcements";
    public static final String ALERTS_CHANNEL = "alerts";

    public static TextChannel getTextChannel(String channelId) {
        return DiscordSRV.getPlugin().getDestinationTextChannelForGameChannelName(channelId);
    }

    public static void sendDiscordMessage(Message message, String channelId) {
        DiscordUtil.queueMessage(getTextChannel(channelId), message);
    }
}
