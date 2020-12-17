package es.diegofpb.vgsocios.data.remote

import es.diegofpb.vgsocios.data.remote.requests.UserLoginRequest
import es.diegofpb.vgsocios.data.remote.responses.UserLoginResponse
import retrofit2.Response
import javax.inject.Inject

class ApiManagerImpl @Inject constructor(
    private val apiService: ApiService) : ApiManager {

    override suspend fun login(userLoginRequest: UserLoginRequest): Response<UserLoginResponse> =
        apiService.login(userLoginRequest)

}