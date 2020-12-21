package es.diegofpb.vgsocios.data.remote

import es.diegofpb.vgsocios.data.remote.requests.AddBookingRequest
import es.diegofpb.vgsocios.data.remote.requests.CancelBookingRequest
import es.diegofpb.vgsocios.data.remote.requests.UserLoginRequest
import es.diegofpb.vgsocios.data.remote.responses.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("User/Login")
    suspend fun login(@Body userDataLogin: UserLoginRequest): Response<UserLoginResponse>

    // Get Membership Information
    @GET("/Member/GetViewMembershipInfo")
    suspend fun getMembershipInfo(
        @Header("Authorization") token: String,
        @Query(value = "contractPersonId", encoded = true) contractPersonId: Int
    ): Response<MembershipInfoResponse>

    // Get Bookings
    @GET("/Class/GetBookingsForPerson")
    suspend fun getBookings(
        @Header("Authorization") token: String,
        @Query(value = "contractPersonId", encoded = true) contractPersonId: Int,
        @Query(value = "fromDate", encoded = true) fromDate: String,
        @Query(value = "toDate", encoded = true) toDate: String
    ): Response<Array<BookingResponse>>

    // Get available Clubs for contractNo
    @GET("/Club/ListAllClubInfosForBookingByContractNo")
    suspend fun getListAllClubInfoForBookingByContractNo(
        @Header("Authorization") token: String,
        @Query(value = "ContractNo") contractNo: Int
    ): Response<Array<ClubListForContractIdResponse>>

    // Add Booking
    @POST("/Class/AddBooking")
    suspend fun addBooking(
        @Header("Authorization") token: String,
        @Body addBookingRequest: AddBookingRequest
    ): Response<AddBookingResponse>

    // Cancel Booking
    @POST("/Class/CancelBooking")
    suspend fun cancelBooking(
        @Header("Authorization") token: String,
        @Body cancelBookingRequest: CancelBookingRequest
    ): Response<CancelBookingResponse>

    // Get Equipment for Booking (Spinning)
    @GET("/Class/ListEquipmentForClass")
    suspend fun getListEquipmentForClass(
        @Header("Authorization") token: String,
        @Query(value = "clubClassId", encoded = true) clubClassId: String
    ): Response<Array<EquipmentForClassResponse>>

}