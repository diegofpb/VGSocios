package es.diegofpb.vgsocios.data.remote

import es.diegofpb.vgsocios.data.remote.requests.UserLoginRequest
import es.diegofpb.vgsocios.data.remote.responses.BookingResponse
import es.diegofpb.vgsocios.data.remote.responses.MembershipInfoResponse
import es.diegofpb.vgsocios.data.remote.responses.UserLoginResponse
import retrofit2.Response

interface ApiManager {

    fun applyToken()

    suspend fun login(userLoginRequest: UserLoginRequest): Response<UserLoginResponse>
    suspend fun getMembershipInfo(contractPersonId: Int): Response<MembershipInfoResponse>
    suspend fun getBookings(contractPersonId: Int, fromDate: String, toDate: String):
            Response<Array<BookingResponse>>
}