package es.diegofpb.vgsocios.data.remote.repositories

import android.util.Log
import es.diegofpb.vgsocios.data.entities.BookingInfo
import es.diegofpb.vgsocios.data.entities.Login
import es.diegofpb.vgsocios.data.entities.MembershipInfo
import es.diegofpb.vgsocios.data.remote.ApiManager
import es.diegofpb.vgsocios.data.remote.requests.UserLoginRequest
import es.diegofpb.vgsocios.data.remote.responses.mapToBookingInfo
import es.diegofpb.vgsocios.data.remote.responses.mapToError
import es.diegofpb.vgsocios.data.remote.responses.mapToLogin
import es.diegofpb.vgsocios.data.remote.responses.mapToMembershipInfo
import es.diegofpb.vgsocios.utils.Resource
import es.diegofpb.vgsocios.utils.Status
import java.lang.Exception
import javax.inject.Inject

class VGRepository @Inject constructor(private val apiManager: ApiManager) {

    fun saveToken() {
        apiManager.applyToken()
    }

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

    suspend fun getViewMembershipInfo(contractPersonId: Int) : Resource<MembershipInfo> {
        val membershipResponse = apiManager.getMembershipInfo(contractPersonId)

        if (membershipResponse.isSuccessful){
            membershipResponse.body()?.let { response ->
                return try {
                    val membershipData = response.mapToMembershipInfo()
                    Resource(Status.SUCCESS, membershipData, "OK")
                } catch (ex: Exception) {
                    val membershipError = response.mapToError()
                    Resource(Status.ERROR, null, membershipError.error)
                }
            }
        }
        Log.d("VGRepository-getViewMembershipInfo", "Out")
        return Resource(Status.ERROR, null, "Error al contactar con el servicio.")
    }


    suspend fun getBookings(contractPersonId: Int, fromDate: String, toDate: String) : Resource<List<BookingInfo>> {
        val bookingsResponse = apiManager.getBookings(contractPersonId, fromDate, toDate)

        if (bookingsResponse.isSuccessful){
            bookingsResponse.body()?.let { response ->
                return try {
                    val bookingList = ArrayList<BookingInfo>()
                    response.forEach {
                        bookingList.add(it.mapToBookingInfo())
                    }
                    Resource(Status.SUCCESS, bookingList, "OK")
                } catch (ex: Exception) {
                    val bookingError = response.mapToError()
                    Resource(Status.ERROR, null, bookingError.error)
                }
            }
        }
        Log.d("VGRepository-getBookings", "Out")
        return Resource(Status.ERROR, null, "Error al contactar con el servicio.")
    }
}