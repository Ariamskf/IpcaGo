package ipca.example.pcmp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ipca.example.pcmp.models.DashboardItem
import ipca.example.pcmp.models.UserProfile

enum class ChartType {
    PIE, BAR
}

class ProfileViewModel : ViewModel() {

    private val _userProfile = MutableLiveData<UserProfile>()
    private val _dashboardItems = MutableLiveData<List<DashboardItem>>()

    val userProfile: LiveData<UserProfile> = _userProfile
    val dashboardItems: LiveData<List<DashboardItem>> = _dashboardItems


    init {
        _userProfile.value = UserProfile(
            name = "Sofia",
            email = "sofiascosta@ipca.pt",
            id = "1055",
            imageUrl = "https://example.com/profile.jpg"
        )

        _dashboardItems.value = listOf(
            DashboardItem(
                title = "UCs",
                description = "Overview of UCs for November 2024",
                type = ChartType.PIE,
                chartData = listOf(30f, 20f, 50f),
                labels = listOf("PCMA", "PIP", "Graphics")
            ),
            DashboardItem(
                title = "Average Weekly Hours",
                description = "+2h/week",
                type = ChartType.BAR,
                chartData = listOf(15f, 11.2f, 8f),
                labels = listOf("S", "T", "Q")
            )
        )
    }
}

