Pushover Notification Plugin for Rundeck
========================================

This plugin provides the ability to send Rundeck start/stop/failure notifications to mobile devices using the [Pushover][pushover] app. 

Installation
------------

Copy the [pushover-notification-plugin.jar][latest_release] to the libext/ directory for Rundeck.


Configuration
-------------

Configuration for this plugin is relatively straightforward.  

1. First, [login to pushover][pushover] and copy your user token.
2. From the [pushover application builder][pushover_app_builder], create a new application for the plugin and copy the application API key once complete.
3. Update the [project.properties][rundeck_project_properties] file for each project you wish to allow Pushover notifications from.  To configure notifications for all projects, update [framework.properties][rundeck_framework_properties] file.

*TIP:  The [new rundeck logo][rundeck_icon] works well as the icon when creating the app in Pushover.*

### project.properties

    project.plugin.Notification.Pushover.appApiToken=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    project.plugin.Notification.Pushover.userIdToken=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

### framework.properties

    framework.plugin.Notification.Pushover.appApiToken=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    framework.plugin.Notification.Pushover.userIdToken=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXX


### Notification Groups

If you wish to send job notifications to more than one user at a time, Pushover allows you to create [notification groups][pushover_group_builder].  To use, simply specify the group token in the \*.properties file for userIdToken.


Usage
-----

Check the Pushover box when specifying notifications for a job.


Limitations
-----------

Currently, Pushover user ID tokens can only be set at the [project or framework][rundeck_plugin_scopes] level.  I may end up modifying the plugin to allow it to be defined at the instance (job) as well, but don't currently have a need for it.


Credits
-------

The [pushover4j][pushover4j] library was used to interact with the Pushover API.


[pushover]: https://pushover.net "Pushover"
[pushover_api]: https://pushover.net/api "Pushover API"
[pushover_app_builder]: https://pushover.net/apps/build
[pushover_group_builder]: https://pushover.net/groups/build
[rundeck_icon]: https://raw2.github.com/rundeck/rundeck/development/rundeckapp/web-app/images/rundeck2-icon-256.png "Rundeck Icon"
[rundeck_plugin_scopes]: http://rundeck.org/docs/developer/workflow-step-plugin-development.html#property-scopes "Rundeck property scopes documentation"
[rundeck_project_properties]: http://rundeck.org/docs/administration/configuration.html#project.properties
[rundeck_framework_properties]: http://rundeck.org/docs/administration/configuration.html#framework.properties
[pushover4j]: https://github.com/sps/pushover4j
[latest_release]: https://github.com/jrbing/pushover-notification-plugin/releases/download/v0.0.2/pushover-notification-plugin-0.0.2.jar
