package ani.shiroin.offline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import ani.shiroin.R
import ani.shiroin.databinding.FragmentOfflineBinding
import ani.shiroin.isOnline
import ani.shiroin.navBarHeight
import ani.shiroin.settings.saving.PrefManager
import ani.shiroin.settings.saving.PrefName
import ani.shiroin.startMainActivity
import ani.shiroin.statusBarHeight

class OfflineFragment : Fragment() {
    private var offline = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOfflineBinding.inflate(inflater, container, false)
        binding.refreshContainer.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            topMargin = statusBarHeight
            bottomMargin = navBarHeight
        }
        offline = PrefManager.getVal(PrefName.OfflineMode)
        binding.noInternet.text =
            if (offline) "Offline Mode" else getString(R.string.no_internet)
        binding.refreshButton.visibility = if (offline) View.GONE else View.VISIBLE
        binding.refreshButton.setOnClickListener {
            if (isOnline(requireContext())) {
                startMainActivity(requireActivity())
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        offline = PrefManager.getVal(PrefName.OfflineMode)
    }
}