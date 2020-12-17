package es.diegofpb.vgsocios.data.remote

import android.content.SharedPreferences
import es.diegofpb.vgsocios.data.remote.requests.UserLoginRequest
import es.diegofpb.vgsocios.data.remote.responses.BookingResponse
import es.diegofpb.vgsocios.data.remote.responses.MembershipInfoResponse
import es.diegofpb.vgsocios.data.remote.responses.UserLoginResponse
import es.diegofpb.vgsocios.utils.Constants.VG_USER_TOKEN
import retrofit2.Response
import javax.inject.Inject

class ApiManagerImpl @Inject constructor(
    private val apiService: ApiService,
    private val sharedPreferences: SharedPreferences
) : ApiManager {

    private var token = ""

    override fun applyToken() {
        token = sharedPreferences.getString(VG_USER_TOKEN, "")!!
    }

    override suspend fun login(userLoginRequest: UserLoginRequest): Response<UserLoginResponse> =
        apiService.login(userLoginRequest)

    override suspend fun getMembershipInfo(contractPersonId: Int): Response<MembershipInfoResponse> =
        apiService.getMembershipInfo("Basic $token", contractPersonId)

    override suspend fun getBookings(contractPersonId: Int, fromDate: String, toDate: String):
            Response<Array<BookingResponse>> =
        apiService.getBookings("Basic $token", contractPersonId, fromDate, toDate)


}