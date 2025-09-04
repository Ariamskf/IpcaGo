package ipca.example.pcmp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ipca.example.pcmp.repository.AppRepository
import ipca.example.pcmp.repository.CheckUserLoggedInUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val checkUserLoggedInUseCase: CheckUserLoggedInUseCase
) : ViewModel() {

    fun onAppStart(navigateToNextScreen: (String) -> Unit) {
        viewModelScope.launch {
            delay(3000) // Simulate splash duration

            val isUserLoggedIn = checkUserLoggedInUseCase()
            if (isUserLoggedIn) {
                navigateToNextScreen("home")
            } else {
                navigateToNextScreen("login")
            }
        }
    }
}


data class SplashScreenState(
    val isLoading: Boolean = true
)
