package org.rundeck.plugins.notification;

import com.dtolabs.rundeck.core.plugins.Plugin;
import com.dtolabs.rundeck.core.plugins.configuration.PropertyScope;
import com.dtolabs.rundeck.plugins.descriptions.PluginDescription;
import com.dtolabs.rundeck.plugins.descriptions.PluginProperty;
import com.dtolabs.rundeck.plugins.notification.NotificationPlugin;
import net.pushover.client.*;
import java.util.*;

/**
 * Pushover Notification Plugin
 */
@Plugin(service = "Notification", name = "Pushover")
@PluginDescription(title = "Pushover", description = "Send notification messages to Pushover")
public class PushoverNotification implements NotificationPlugin {

	@PluginProperty(name = "appApiToken", description = "Application API Token", scope = PropertyScope.Project)
	private String appApiToken;

	@PluginProperty(name = "userIdToken", description = "User ID Token", scope = PropertyScope.Project)
	private String userIdToken;

	/**
	 * Post a notification for the given trigger, dataset, and configuration
	 *
	 * @param trigger event type causing notification
	 * @param executionData execution data
	 * @param config notification configuration
	 * @return
	 */
	@Override
	public boolean postNotification(String trigger, Map executionData, Map config) {
		if (isBlank(appApiToken) || isBlank(userIdToken)) {
			throw new IllegalStateException("appApiToken and userIdToken must be set");
		}

        String jobString = ((Map)executionData.get("job")).get("name").toString();
        String projectString = executionData.get("project").toString();

		PushoverClient client = new PushoverRestClient();

        try {
            client.pushMessage(PushoverMessage.builderWithApiToken(appApiToken)
                    .setUserId(userIdToken)
                    .setMessage(getNotificationMessage(trigger, jobString, projectString))
                    .build());
        } catch (PushoverException e) {
                    e.printStackTrace();
                    return false;
        }

        System.err.printf("Trigger was: %s \n", trigger);
        System.err.printf("Execution data was: %s \n", executionData);
        System.err.printf("Config was: %s\n", config);
        System.err.printf("Jobdata was: %s\n", jobString);
        System.err.printf("Project was: %s\n", projectString);

		return true;

	}

	private boolean isBlank(String string) {
		return null == string || "".equals(string);
	}
	
	private String getNotificationMessage(String trigger, String job, String project) {
		String notificationMessage = null;
				switch (trigger) {
			case "start":
				notificationMessage = "Rundeck job " + job + " for project " + project + " has begun";
				break;
			case "success":
				notificationMessage = "Rundeck job " + job + " for project " + project + " has finished successfully";
				break;
			case "failure":
				notificationMessage = "Rundeck job " + job + " for project " + project + " has failed";
				break;
		}
		return notificationMessage;
	}

}
