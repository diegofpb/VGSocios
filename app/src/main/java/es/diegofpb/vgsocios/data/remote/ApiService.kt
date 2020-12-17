package es.diegofpb.vgsocios.data.remote

import es.diegofpb.vgsocios.data.remote.requests.UserLoginRequest
import es.diegofpb.vgsocios.data.remote.responses.BookingResponse
import es.diegofpb.vgsocios.data.remote.responses.MembershipInfoResponse
import es.diegofpb.vgsocios.data.remote.responses.UserLoginResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("User/Login")
    suspend fun login(@Body userDataLogin: UserLoginRequest): Response<UserLoginResponse>

    // Get Membership Information
    @GET("/Member/GetViewMembershipInfo")
    suspend fun getMembershipInfo(@Header("Authorization") token: String,
                                  @Query(value = "contractPersonId", encoded = true) contractPersonId: Int): Response<MembershipInfoResponse>

    // Get Bookings
    @GET("/Class/GetBookingsForPerson")
    suspend fun getBookings(@Header("Authorization") token: String,
                            @Query(value = "contractPersonId", encoded = true) contractPersonId: Int,
                            @Query(value = "fromDate", encoded = true) fromDate: String,
                            @Query(value = "toDate", encoded = true) toDate: String): Response<Array<BookingResponse>>

}