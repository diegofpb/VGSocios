package es.diegofpb.vgsocios.data.remote.responses

import es.diegofpb.vgsocios.data.entities.Login

fun UserLoginResponse.mapToLogin() = Login(
    token = token,
    contractPersonId = contractPersonId,
    contractNo = contractNo,
    clubNo = clubNo
)

fun UserLoginResponse.mapToError() = ErrorResponse(
    error = error,
    errorCode = errorCode
)