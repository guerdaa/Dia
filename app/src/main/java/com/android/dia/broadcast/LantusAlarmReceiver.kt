package com.android.dia.broadcast

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.android.dia.R
import android.app.NotificationChannel
import android.os.Build.VERSION_CODES.O
import android.os.Build
import android.app.NotificationManager
import android.graphics.Color
import android.os.Vibrator




class LantusAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("~~~~~~~~~~~~~", "Launched1")
        val time = intent?.getLongExtra("TIME", 0)!!
        //if(System.currentTimeMillis() > time + 4 * 60 * 1000)
          //  return
        Log.d("~~~~~~~~~~~~~", "Launched2")
        val v = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        v.vibrate(2000)
        val mNotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context!!, "1221")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Lantus Alarm")
            .setContentText("It's Lantus time. Dont't forget to inject")
            .setDefaults(Notification.DEFAULT_VIBRATE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val channelId = "Your_channel_id"
        if (Build.VERSION.SDK_INT >= O) {
            val channel =
                NotificationChannel(
                    channelId,
                    "Lantus channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
            channel.enableLights(true)
            channel.lightColor = Color.GREEN
            mNotificationManager.createNotificationChannel(channel)
        }
        builder.setChannelId(channelId)
        mNotificationManager.notify(1212, builder.build())

    }
}