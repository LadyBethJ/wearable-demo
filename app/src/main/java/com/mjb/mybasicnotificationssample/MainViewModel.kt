package com.mjb.mybasicnotificationssample


import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModel : ViewModel() {

    private val notificationId: Int = 1
    private lateinit var CHANNEL_ID: String
    private lateinit var context: Context

    fun onButtonClicked(myContext: Context, channelId: String) {
        context = myContext
        CHANNEL_ID = channelId
        generateNotification()
    }

    private fun generateNotification(){
        //intent to respond the click on the notification
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("https://developer.android.com/develop/ui/views/notifications")
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )


        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(R.string.push_text))
            .setSubText(context.getString(R.string.push_subtext))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(notificationId, builder.build())
        }
    }

}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class MyViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}