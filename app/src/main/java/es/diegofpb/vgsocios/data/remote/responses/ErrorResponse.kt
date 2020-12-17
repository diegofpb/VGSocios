package es.diegofpb.vgsocios.data.remote.responses

data class ErrorResponse(
    //TODO: ADD serializedName
    val errorCode: Int? = null,
    val error: String?
)