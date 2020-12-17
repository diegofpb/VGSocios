package es.diegofpb.vgsocios.data.remote

import es.diegofpb.vgsocios.data.remote.requests.UserLoginRequest
import es.diegofpb.vgsocios.data.remote.responses.UserLoginResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("User/Login")
    suspend fun login(@Body userDataLogin: UserLoginRequest): Response<UserLoginResponse>

}