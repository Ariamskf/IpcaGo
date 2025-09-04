package ipca.example.pcmp.ui.login


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

data class LoginState(
    val email: String? = null,
    val password :String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class LoginViewModel  : ViewModel(){

    var state by mutableStateOf(LoginState())
        private set

    fun onEmailChange(newValue:String){
        state = state.copy( email = newValue)
    }

    fun onPasswordChange(newValue:String){
        state = state.copy( password = newValue)
    }

    fun login(onLoginSuccess: ()->Unit ){

        if(state.email.isNullOrEmpty()) {
            state = state.copy( error = "email is empty")
            return
        }

        if(state.password.isNullOrEmpty()) {
            state = state.copy( error = "password is empty")
            return
        }
        state = state.copy( isLoading = true)
        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(state.email!!, state.password!!)
            .addOnCompleteListener { task ->
                state = state.copy( isLoading = false)
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    onLoginSuccess()
                } else {
                    // If sign in fails, display a message to the user.
                    state = state.copy( error = task.exception?.message)
                }
            }
    }


}