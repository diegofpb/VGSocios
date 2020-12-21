package es.diegofpb.vgsocios.data.remote.requests

import com.google.gson.annotations.SerializedName

data class CancelBookingRequest(
    @SerializedName("contractPersonId") val contractPersonId: String? = null,
    @SerializedName("clubClassId") val clubClassId: String? = null,
    @SerializedName("isTimeBased") val isTimeBased: Boolean? = null
)