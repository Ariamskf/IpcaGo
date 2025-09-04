package ipca.example.pcmp.models

import ipca.example.pcmp.ui.profile.ChartType


data class UserProfile(
    val name: String,
    val email: String,
    val id: String,
    val imageUrl: String
)

data class DashboardItem(
    val title: String,
    val description: String,
    val type: ChartType, // Corridor para usar o enum ChartType
    val chartData: List<Float>,
    val labels: List<String>
)