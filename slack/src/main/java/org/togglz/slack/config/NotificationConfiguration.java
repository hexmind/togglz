package org.togglz.slack.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.togglz.core.repository.FeatureState;
import org.togglz.slack.notification.NotificationComposer;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Objects.firstNonNull;
import static com.google.common.base.Preconditions.checkArgument;
import static org.togglz.core.util.Strings.isNotBlank;

public class NotificationConfiguration {

    private static final List<String> DEFAULT_CHANNELS = ImmutableList.of("toggles");
    private static final ArrayList<String> DEFAULT_STATE_ICONS = Lists.newArrayList("large_blue_circle", "white_circle");
    private static final ArrayList<String> DEFAULT_CHANGE_VERBS = Lists.newArrayList("enabled", "disabled");
    private static final String DEFAULT_APP_ICON = "joystick";

    private final String slackHookUrl;
    private final List<String> channels;
    private final String togglzAdminConsoleUrl;
    private final String appName;
    private final String messageFormat;
    private final String appIcon;
    private final List<String> stateIcons;
    private final List<String> changeVerbs;
    private final boolean disabledAsyncSender;

    public static NotificationConfigurationBuilder builder() {
        return new NotificationConfigurationBuilder();
    }

    NotificationConfiguration(String slackHookUrl,
                              List<String> channels,
                              String togglzAdminConsoleUrl,
                              String appName,
                              String messageFormat,
                              String appIcon,
                              List<String> stateIcons,
                              List<String> changeVerbs,
                              boolean disabledAsyncSender) {
        checkArgument(isNotBlank(slackHookUrl), "slackHookUrl is required");
        checkArgument(slackHookUrl.startsWith("http"), "slackHookUrl isn't valid url: %s", slackHookUrl);
        this.slackHookUrl = slackHookUrl;
        this.channels = firstNonNull(channels, DEFAULT_CHANNELS);
        this.togglzAdminConsoleUrl = firstNonNull(togglzAdminConsoleUrl, "");
        this.appName = firstNonNull(appName, "");
        this.messageFormat = firstNonNull(messageFormat, NotificationComposer.DEFAULT_MESSAGE_FORMAT);
        this.appIcon = firstNonNull(appIcon, DEFAULT_APP_ICON);
        this.stateIcons = firstNonNull(stateIcons, DEFAULT_STATE_ICONS);
        this.changeVerbs = firstNonNull(changeVerbs, DEFAULT_CHANGE_VERBS);
        this.disabledAsyncSender = disabledAsyncSender;
    }

    public String getSlackHookUrl() {
        return slackHookUrl;
    }

    public List<String> getChannels() {
        return channels;
    }

    public String getTogglzAdminConsoleUrl() {
        return togglzAdminConsoleUrl;
    }

    public String getAppName() {
        return appName;
    }

    public String getMessageFormat() {
        return messageFormat;
    }

    public String getAppIcon() {
        return appIcon;
    }

    public String getStateIcon(FeatureState state) {
        return stateIcons.get(state.isEnabled() ? 0 : 1);
    }

    public String getChangeVerb(FeatureState state) {
        return changeVerbs.get(state.isEnabled() ? 0 : 1);
    }

    public boolean isDisabledAsyncSender() {
        return disabledAsyncSender;
    }
}
