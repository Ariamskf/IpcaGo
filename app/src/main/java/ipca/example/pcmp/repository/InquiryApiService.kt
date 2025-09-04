package ipca.example.pcmp.repository

import ipca.example.pcmp.models.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface InquiryApiService {
    @POST("submit_inquiry") // Replace with the actual API endpoint
    suspend fun submitInquiry(@Body user: UserModel): Response<Void> // Adjust response type as needed
}
