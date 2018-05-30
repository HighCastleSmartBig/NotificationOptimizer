package com.ubi.notificationoptimizer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.provider.Settings

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!isEnabledReadNotification()) {
            showNotificationAccessSettingMenu()
        } else {

        }
    }

    private fun showNotificationAccessSettingMenu() {
        val intent = Intent()
        intent.action = Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
        startActivity(intent)
    }

    private fun isEnabledReadNotification(): Boolean {
        val contentResolver = contentResolver
        val rawListeners = Settings.Secure.getString(contentResolver,
                "enabled_notification_listeners")
        if (rawListeners == null || "" == rawListeners) {
            return false
        } else {
            val listeners = rawListeners.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (listener in listeners) {
                if (listener.startsWith(packageName)) {
                    return true
                }
            }
        }
        return false
    }



}
