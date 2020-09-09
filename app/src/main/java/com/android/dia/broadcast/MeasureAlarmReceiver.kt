package com.android.dia.broadcast

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Vibrator
import androidx.core.app.NotificationCompat
import com.android.dia.R

class MeasureAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val v = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(2000)
        val mNotificationManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "Measure channel ID"
        val builder = NotificationCompat.Builder(context!!, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Measure Alarm")
            .setContentText("Check your blood sugar")
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    channelId,
                    "Measure channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
            channel.enableLights(true)
            channel.lightColor = Color.GREEN
            mNotificationManager.createNotificationChannel(channel)
        }
        builder.setChannelId(channelId)
        mNotificationManager.notify(1121, builder.build())
    }

}