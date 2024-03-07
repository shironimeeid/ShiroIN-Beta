package ani.shiroin.connections.mal

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ani.shiroin.R
import ani.shiroin.client
import ani.shiroin.connections.mal.MAL.clientId
import ani.shiroin.connections.mal.MAL.saveResponse
import ani.shiroin.logError
import ani.shiroin.settings.saving.PrefManager
import ani.shiroin.settings.saving.PrefName
import ani.shiroin.snackString
import ani.shiroin.startMainActivity
import ani.shiroin.themes.ThemeManager
import ani.shiroin.tryWithSuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ThemeManager(this).applyTheme()
        try {
            val data: Uri = intent?.data
                ?: throw Exception(getString(R.string.mal_login_uri_not_found))
            val codeChallenge = PrefManager.getVal(PrefName.MALCodeChallenge, null as String?)
                ?: throw Exception(getString(R.string.mal_login_code_challenge_not_found))
            val code = data.getQueryParameter("code")
                ?: throw Exception(getString(R.string.mal_login_code_not_present))

            snackString(getString(R.string.logging_in_mal))
            lifecycleScope.launch(Dispatchers.IO) {
                tryWithSuspend(true) {
                    val res = client.post(
                        "https://myanimelist.net/v1/oauth2/token",
                        data = mapOf(
                            "client_id" to clientId,
                            "code" to code,
                            "code_verifier" to codeChallenge,
                            "grant_type" to "authorization_code"
                        )
                    ).parsed<MAL.ResponseToken>()
                    saveResponse(res)
                    MAL.token = res.accessToken
                    snackString(getString(R.string.getting_user_data))
                    MAL.query.getUserData()
                    launch(Dispatchers.Main) {
                        startMainActivity(this@Login)
                    }
                }
            }
        } catch (e: Exception) {
            logError(e, snackbar = false)
            startMainActivity(this)
        }
    }

}