package ipca.example.pcmp.ui.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel


class UserDataViewModel : ViewModel() {
    val firstName: MutableState<String> = mutableStateOf("")
    val lastName: MutableState<String> = mutableStateOf("")
    val contact: MutableState<String> = mutableStateOf("")

    fun saveData() {

    }
}