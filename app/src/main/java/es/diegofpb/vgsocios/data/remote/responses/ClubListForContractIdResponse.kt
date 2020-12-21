package es.diegofpb.vgsocios.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ClubListForContractIdResponse(

    @SerializedName("urlName")
    val urlName: Any? = null,

    @SerializedName("keywords")
    val keywords: Any? = null,

    @SerializedName("clubDesc")
    val clubDesc: Any? = null,

    @SerializedName("latitude")
    val latitude: Double? = null,

    @SerializedName("postalCode")
    val postalCode: Any? = null,

    @SerializedName("clubName")
    val clubName: String? = null,

    @SerializedName("clubTimesList")
    val clubTimesList: Any? = null,

    @SerializedName("description")
    val description: Any? = null,

    @SerializedName("autonomousRegion")
    val autonomousRegion: Any? = null,

    @SerializedName("title")
    val title: Any? = null,

    @SerializedName("clubManagerName")
    val clubManagerName: Any? = null,

    @SerializedName("clubNo")
    val clubNo: Int? = null,

    @SerializedName("isHeadOffice")
    val isHeadOffice: Boolean? = null,

    @SerializedName("address4")
    val address4: Any? = null,

    @SerializedName("openingDate")
    val openingDate: Any? = null,

    @SerializedName("longitude")
    val longitude: Double? = null,

    @SerializedName("address3")
    val address3: Any? = null,

    @SerializedName("address2")
    val address2: Any? = null,

    @SerializedName("address1")
    val address1: Any? = null,

    @SerializedName("telephone")
    val telephone: Any? = null,

    @SerializedName("isConverted")
    val isConverted: Boolean? = null,

    @SerializedName("closingDate")
    val closingDate: Any? = null,

    @SerializedName("showMedia")
    val showMedia: Boolean? = null,

    @SerializedName("isOpen")
    val isOpen: Int? = null,

    @SerializedName("regionDesc")
    val regionDesc: Any? = null
)
