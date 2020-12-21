package es.diegofpb.vgsocios.data.remote.responses

import com.google.gson.annotations.SerializedName

data class BookingResponse(
    @SerializedName("classTypeName") val classTypeName: String? = null,
    @SerializedName("clubDesc") val clubDesc: String? = null,
    @SerializedName("displayDate") val displayDate: String? = null,
    @SerializedName("bookingDate") val bookingDate: String? = null,
    @SerializedName("clubClassId") val clubClassId: Int? = null,
    @SerializedName("isTimeBased") val isTimeBased: Boolean? = null,

    val errorCode: Int? = null,
    val error: String?
)