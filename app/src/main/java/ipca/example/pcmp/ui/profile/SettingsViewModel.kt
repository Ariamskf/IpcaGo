package ipca.example.pcmp.ui.profile

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

open class SettingsViewModel: ViewModel() {

    private val _isDarkModeEnabled = mutableStateOf(false)
    open val isDarkModeEnabled: Boolean get() = _isDarkModeEnabled.value

    open fun toggleDarkMode() {
        _isDarkModeEnabled.value = !_isDarkModeEnabled.value
    }

    open fun onEditData() {}
    open fun onFavorites() {}
    open fun onNotifications() {}
    open fun onVersionInfo() {}
    open fun onTermsAndConditions() {}
    open fun onPrivacyPolicy() {}
    open fun onLogout() {}
    open fun onNotifications(context: Context) {}
}

