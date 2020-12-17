package es.diegofpb.vgsocios.data.remote.responses

data class UserLoginResponse(
    //TODO: ADD serializedName
    val userTypeId: Int? = null,
    val firstTimeLogin: Boolean? = null,
    val isSuspended: Boolean? = null,
    val isUnpaidCancel: Boolean? = null,
    val contractNo: Int,
    val clubDesc: String? = null,
    val roles: List<Any?>? = null,
    val contractPersonId: Int,
    val userIdentityId: Int? = null,
    val userCode: String? = null,
    val clubNo: Int,
    val token: String,
    val firstTimeLoginToken: String? = null,
    val firstName: String? = null,
    val hasPrepayContract: Boolean? = null,
    val cardId: Any? = null,
    val isOneTime: Boolean? = null,
    val isFrozen: Boolean? = null,
    val firstTimeLoginExpired: Boolean? = null,
    val hasPreOpenOverride: Boolean? = null,
    val username: String? = null,
    val feeTypeId: Int? = null,

    val errorCode: Int? = null,
    val error: String?
)