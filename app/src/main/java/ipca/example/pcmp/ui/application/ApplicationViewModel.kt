package ipca.example.pcmp.ui.application

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class ApplicationViewModel : ViewModel() {
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var email by mutableStateOf("")
    var countryCode by mutableStateOf("")
    var contactNumber by mutableStateOf("")
    var message by mutableStateOf("")
    var cvFileName by mutableStateOf("Anexar CV")
    var selectedCVUri: Uri? = null

    private val db = FirebaseFirestore.getInstance() // Firestore instance

    fun onCVSelected(uri: Uri, fileName: String) {
        selectedCVUri = uri
        cvFileName = fileName
    }

    private val storage = FirebaseStorage.getInstance()

    fun uploadCVAndSubmitApplication(context: Context) {
        selectedCVUri?.let { uri ->
            val storageRef: StorageReference =
                storage.reference.child("cvs/${System.currentTimeMillis()}_${cvFileName}")

            storageRef.putFile(uri)
                .addOnSuccessListener { taskSnapshot ->
                    storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        submitApplicationToFirestore(context, downloadUri.toString())
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "CV Upload Failed: ${e.message}", Toast.LENGTH_LONG)
                        .show()
                }
        } ?: run {
            submitApplicationToFirestore(context, null) // Submit without CV
        }
    }

    fun submitApplicationToFirestore(context: Context, cvUrl: String?) {
        val applicationData = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "countryCode" to countryCode,
            "contactNumber" to contactNumber,
            "message" to message,
            "cvFileName" to cvFileName,
            "cvUrl" to cvUrl // Store CV download URL
        )

        db.collection("applications")
            .add(applicationData)
            .addOnSuccessListener {
                Toast.makeText(context, "Application Submitted Successfully", Toast.LENGTH_LONG)
                    .show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
