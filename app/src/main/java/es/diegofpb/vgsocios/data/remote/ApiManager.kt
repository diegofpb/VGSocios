package es.diegofpb.vgsocios.data.remote

import es.diegofpb.vgsocios.data.remote.requests.UserLoginRequest
import es.diegofpb.vgsocios.data.remote.responses.UserLoginResponse
import retrofit2.Response

interface ApiManager {

    suspend fun login(userLoginRequest: UserLoginRequest): Response<UserLoginResponse>

}