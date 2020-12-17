package es.diegofpb.vgsocios.data.entities

data class Login(
    val token: String,
    val contractPersonId: Int,
    val contractNo: Int,
    val clubNo: Int
)
