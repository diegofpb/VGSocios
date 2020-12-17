package es.diegofpb.vgsocios.data.remote.requests

import com.google.gson.annotations.SerializedName

data class UserLoginRequest(
	@SerializedName("Username") val username: String? = null,
	@SerializedName("Password") val password: String? = null
)

