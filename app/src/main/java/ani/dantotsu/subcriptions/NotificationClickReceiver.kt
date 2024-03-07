package ani.shiroin.subcriptions

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ani.shiroin.INCOGNITO_CHANNEL_ID
import ani.shiroin.settings.saving.PrefManager
import ani.shiroin.settings.saving.PrefName


class NotificationClickReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        PrefManager.setVal(PrefName.Incognito, false)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(INCOGNITO_CHANNEL_ID)

    }
}