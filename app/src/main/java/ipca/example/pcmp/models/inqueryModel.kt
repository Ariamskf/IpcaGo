package ipca.example.pcmp.models

data class UserModel(
    val firstName: String = "",
    val lastName: String = "",
    val preferredUcs: String = "",
    val selectedCampus: String = "Polo do IPCA",
    val selectedHours: String = "Horas",
    val termsAccepted: Boolean = false
)
