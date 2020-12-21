package es.diegofpb.vgsocios.data.remote.requests

import com.google.gson.annotations.SerializedName

data class AddBookingRequest(
    @SerializedName("contractPersonId") val contractPersonId: Int? = null,
    @SerializedName("clubClassId") val clubClassId: Int? = null,
    @SerializedName("equipmentNo") val equipmentNo: Int? = null,
    @SerializedName("isInduction") val isInduction: Boolean? = null,
    @SerializedName("isTimeBased") val isTimeBased: Boolean? = null

)