package ani.shiroin.connections

import ani.shiroin.R
import ani.shiroin.Refresh
import ani.shiroin.connections.anilist.Anilist
import ani.shiroin.connections.mal.MAL
import ani.shiroin.currContext
import ani.shiroin.media.Media
import ani.shiroin.settings.saving.PrefManager
import ani.shiroin.settings.saving.PrefName
import ani.shiroin.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun updateProgress(media: Media, number: String) {
    val incognito: Boolean = PrefManager.getVal(PrefName.Incognito)
    if (!incognito) {
        if (Anilist.userid != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val a = number.toFloatOrNull()?.toInt()
                if ((a ?: 0) > (media.userProgress ?: 0)) {
                    Anilist.mutation.editList(
                        media.id,
                        a,
                        status = if (media.userStatus == "REPEATING") media.userStatus else "CURRENT"
                    )
                    MAL.query.editList(
                        media.idMAL,
                        media.anime != null,
                        a, null,
                        if (media.userStatus == "REPEATING") media.userStatus!! else "CURRENT"
                    )
                    toast(currContext()?.getString(R.string.setting_progress, a))
                }
                media.userProgress = a
                Refresh.all()
            }
        } else {
            toast(currContext()?.getString(R.string.login_anilist_account))
        }
    } else {
        toast("Sneaky sneaky :3")
    }
}