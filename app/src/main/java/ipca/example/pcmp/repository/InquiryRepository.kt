package ipca.example.pcmp.repository

import ipca.example.pcmp.models.UserModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InquiryRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://your-api-url.com/") // Replace with your actual base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(InquiryApiService::class.java)

    suspend fun submitInquiry(user: UserModel): Response<Void> {
        return apiService.submitInquiry(user)
    }
}
