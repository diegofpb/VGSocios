package es.diegofpb.vgsocios.data.remote.responses

import com.google.gson.annotations.SerializedName

data class AddBookingResponse(
    @SerializedName("result") val result: String? = null
)