package ipca.example.pcmp.models


data class Application(
    var docId: String? = null, // Firestore document ID
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var countryCode: String = "",
    var contactNumber: String = "",
    var message: String = "",
    var cvFileName: String = "Anexar CV",
    var cvUrl: String? = null // Firestore Storage URL for the CV
)
