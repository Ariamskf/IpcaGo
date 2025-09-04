package ipca.example.pcmp.ui.inquiry

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ipca.example.pcmp.models.UserModel
import ipca.example.pcmp.repository.InquiryRepository
import kotlinx.coroutines.launch


class inquiryViewModel: ViewModel() {
    private val repository = InquiryRepository()
    var user by mutableStateOf(UserModel())
        private set

    val campuses = listOf("Polo do IPCA", "Polo de Guimarães", "Polo de Vila Verde")
    val hours = listOf("Manhã", "Tarde", "Noite")

    var errorMessage by mutableStateOf("")
    private set
    var submissionSuccess by mutableStateOf(false)

    fun updateFirstName(value: String) {
        user = user.copy(firstName = value)
    }

    fun updateLastName(value: String) {
        user = user.copy(lastName = value)
    }

    fun updatePreferredUcs(value: String) {
        user = user.copy(preferredUcs = value)
    }

    fun updateSelectedCampus(value: String) {
        user = user.copy(selectedCampus = value)
    }

    fun updateSelectedHours(value: String) {
        user = user.copy(selectedHours = value)
    }

    fun updateTermsAccepted(value: Boolean) {
        user = user.copy(termsAccepted = value)
    }

    fun validateForm(): Boolean {
        if (user.firstName.isBlank()) {
            errorMessage = "O Primeiro Nome é obrigatório."
            return false
        }
        if (user.lastName.isBlank()) {
            errorMessage = "O Apelido é obrigatório."
            return false
        }
        if (user.preferredUcs.isBlank()) {
            errorMessage = "Por favor escreva as UC’s que gostaria de lecionar."
            return false
        }
        if (user.selectedCampus.isBlank()) {
            errorMessage = "Selecione um Polo de Preferência."
            return false
        }
        if (user.selectedHours.isBlank()) {
            errorMessage = "Selecione a Disposição de Horas."
            return false
        }
        if (!user.termsAccepted) {
            errorMessage = "É necessário aceitar os termos e condições."
            return false
        }
        errorMessage = ""
        return true
    }


    fun submitForm() {
        viewModelScope.launch {
            try {
                val response = repository.submitInquiry(user)
                if (response.isSuccessful) {
                    // Handle success (e.g., navigate to a success screen)
                   submissionSuccess  =true

                } else {
                    // Handle server-side error
                    errorMessage = "Erro ao enviar a candidatura: ${response.code()}"
                }
            } catch (e: Exception) {
                // Handle client-side or network error
                errorMessage = "Erro de rede: ${e.message}"
            }

        }
    }



}



