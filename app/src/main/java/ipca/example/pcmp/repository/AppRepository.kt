package ipca.example.pcmp.repository

import android.content.SharedPreferences
import javax.inject.Inject

interface AppRepository {
    fun isUserLoggedIn(): Boolean
}

class CheckUserLoggedInUseCase @Inject constructor(
    private val appRepository: SharedPreferences
) {
    operator fun invoke(): Boolean {
        return appRepository.getBoolean("isUserLoggedIn", false)
    }
}
