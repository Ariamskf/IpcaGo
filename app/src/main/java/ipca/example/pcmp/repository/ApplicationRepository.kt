package ipca.example.pcmp.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ipca.example.pcmp.models.Application

object ApplicationRepository {

    private val db = Firebase.firestore

    fun add(application: Application) {
        db.collection("applications")
            .add(application)
    }

    fun remove(applicationId: String) {
        db.collection("applications")
            .document(applicationId)
            .delete()
    }

    fun update(application: Application) {
        application.docId?.let { docId ->
            db.collection("applications")
                .document(docId)
                .set(application)
        }
    }

    fun getAll(onResult: (List<Application>, String?) -> Unit) {
        db.collection("applications")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    onResult(emptyList(), e.message)
                    return@addSnapshotListener
                }

                val applications = ArrayList<Application>()
                value?.forEach { doc ->
                    val application = doc.toObject(Application::class.java)
                    application.docId = doc.id
                    applications.add(application)
                }
                onResult(applications, null)
            }
    }
}
