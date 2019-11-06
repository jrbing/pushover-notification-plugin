package com.plugin.pushovernotificationplugin;

/** @author JR Bing */
public class PushoverNotificationPluginException extends RuntimeException {

  /**
   * Constructor.
   *
   * @param message error message
   */
  public PushoverNotificationPluginException(String message) {
    super(message);
  }

  /**
   * Constructor.
   *
   * @param message error message
   * @param cause exception cause
   */
  public PushoverNotificationPluginException(String message, Throwable cause) {
    super(message, cause);
  }
}
