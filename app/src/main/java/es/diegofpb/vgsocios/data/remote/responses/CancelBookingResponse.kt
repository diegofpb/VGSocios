package es.diegofpb.vgsocios.data.remote.responses

import com.google.gson.annotations.SerializedName

data class CancelBookingResponse(
    @SerializedName("result") val result: String? = null
)