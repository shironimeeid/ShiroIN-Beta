package ani.shiroin.connections.anilist

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ani.shiroin.logError
import ani.shiroin.logger
import ani.shiroin.settings.saving.PrefManager
import ani.shiroin.settings.saving.PrefName
import ani.shiroin.startMainActivity
import ani.shiroin.themes.ThemeManager

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ThemeManager(this).applyTheme()
        val data: Uri? = intent?.data
        logger(data.toString())
        try {
            Anilist.token =
                Regex("""(?<=access_token=).+(?=&token_type)""").find(data.toString())!!.value
            PrefManager.setVal(PrefName.AnilistToken, Anilist.token ?: "")
        } catch (e: Exception) {
            logError(e)
        }
        startMainActivity(this)
    }
}
