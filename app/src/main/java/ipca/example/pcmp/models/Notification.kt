package ipca.example.pcmp.models

data class Notification(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val isRead: Boolean,
    val categoryIcon: Int,
    val route: String? = null
)
