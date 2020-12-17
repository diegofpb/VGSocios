package es.diegofpb.vgsocios.data.remote.repositories

import android.util.Log
import es.diegofpb.vgsocios.data.entities.Login
import es.diegofpb.vgsocios.data.remote.ApiManager
import es.diegofpb.vgsocios.data.remote.requests.UserLoginRequest
import es.diegofpb.vgsocios.data.remote.responses.mapToError
import es.diegofpb.vgsocios.data.remote.responses.mapToLogin
import es.diegofpb.vgsocios.utils.Resource
import es.diegofpb.vgsocios.utils.Status
import java.lang.Exception
import javax.inject.Inject

class VGRepository @Inject constructor(private val apiManager: ApiManager) {

    suspend fun login(username: String, password: String): Resource<Login> {
        Log.d("VGRepository-login", "Enter")
        val userLoginRequest = UserLoginRequest(username, password)
        val loginResponse = apiManager.login(userLoginRequest)

        if (loginResponse.isSuccessful) {
            loginResponse.body()?.let { response ->
                return try {
                    val loginData = response.mapToLogin()
                    Resource(Status.SUCCESS, loginData, "OK")
                } catch (ex: Exception) {
                    val loginError = response.mapToError()
                    Resource(Status.ERROR, null, loginError.error)
                }
            }
        }
        Log.d("VGRepository-login", "Out")
        return Resource(Status.ERROR, null, "Error al contactar con el servicio.")
    }

}