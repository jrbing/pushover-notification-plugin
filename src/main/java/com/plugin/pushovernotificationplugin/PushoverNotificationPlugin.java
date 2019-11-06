package com.plugin.pushovernotificationplugin;

import com.dtolabs.rundeck.core.plugins.Plugin;
import com.dtolabs.rundeck.core.plugins.configuration.PropertyScope;
import com.dtolabs.rundeck.plugins.descriptions.PluginDescription;
import com.dtolabs.rundeck.plugins.descriptions.PluginProperty;
import com.dtolabs.rundeck.plugins.notification.NotificationPlugin;
import com.plugin.pushovernotificationplugin.pushover.PushoverClient;
import com.plugin.pushovernotificationplugin.pushover.PushoverException;
import com.plugin.pushovernotificationplugin.pushover.PushoverMessage;
import com.plugin.pushovernotificationplugin.pushover.PushoverRestClient;
import java.util.*;

/** Pushover Notification Plugin. */
@Plugin(service = "Notification", name = "Pushover")
@PluginDescription(title = "Pushover", description = "Send notification messages to Pushover")
public class PushoverNotificationPlugin implements NotificationPlugin {

  @PluginProperty(
      name = "appApiToken",
      description = "Application API Token",
      scope = PropertyScope.Project)
  private String appApiToken;

  @PluginProperty(
      name = "userIdToken",
      description = "User ID Token",
      scope = PropertyScope.Project)
  private String userIdToken;

  /**
   * Post a notification for the given trigger, dataset, and configuration.
   *
   * @param trigger event type causing notification
   * @param executionData execution data
   * @param config notification configuration
   * @throws PushoverNotificationPluginException when any error occurs sending the Pushover
   *     notification
   * @return true, if the Pushover notification was sent successfully
   */
  @Override
  public boolean postNotification(String trigger, Map executionData, Map config) {
    if (isBlank(appApiToken) || isBlank(userIdToken)) {
      throw new IllegalStateException("appApiToken and userIdToken must be set");
    }

    String message = generateMessage(trigger, executionData);

    PushoverClient client = new PushoverRestClient();

    try {
      client.pushMessage(
          PushoverMessage.builderWithApiToken(appApiToken)
              .setUserId(userIdToken)
              .setMessage(generateMessage(trigger, executionData))
              .build());
    } catch (PushoverException e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }

  private boolean isBlank(String string) {
    return null == string || "".equals(string);
  }

  /**
   * Format the message to send.
   *
   * @param trigger Job trigger event
   * @param executionData Job execution data
   * @return String formatted with job data
   */
  private String generateMessage(String trigger, Map executionData) {
    String notificationMessage = null;
    Object job = executionData.get("job");
    Map jobdata = (Map) job;
    Object jobname = jobdata.get("name");
    String projectName = executionData.get("project").toString();

    switch (trigger) {
      case "start":
        notificationMessage =
            "Rundeck job " + jobname + " for project " + projectName + " has started";
        break;
      case "success":
        notificationMessage =
            "Rundeck job " + jobname + " for project " + projectName + " has finished successfully";
        break;
      case "failure":
        notificationMessage =
            "Rundeck job " + jobname + " for project " + projectName + " has failed";
        break;
      case "avgduration":
        notificationMessage =
            "Rundeck job "
                + jobname
                + " for project "
                + projectName
                + " has exceeded the average duration";
        break;
      case "retryablefailure":
        notificationMessage =
            "Rundeck job "
                + jobname
                + " for project "
                + projectName
                + " has failed but will be retried";
        break;
      default:
        break;
    }
    return notificationMessage;
  }
}
