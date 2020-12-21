package es.diegofpb.vgsocios.data.remote.responses

import com.google.gson.annotations.SerializedName

data class EquipmentForClassResponse(
    @SerializedName("classTypeName") val classTypeName: String? = null,
    @SerializedName("clubDesc") val clubDesc: String? = null,
    @SerializedName("displayDate") val displayDate: String? = null,
    @SerializedName("bookingDate") val bookingDate: String? = null
)