package ani.shiroin.widgets

import android.content.Intent
import android.widget.RemoteViewsService
import ani.shiroin.logger

class CurrentlyAiringRemoteViewsService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        logger("CurrentlyAiringRemoteViewsFactory onGetViewFactory")
        return CurrentlyAiringRemoteViewsFactory(applicationContext, intent)
    }
}
