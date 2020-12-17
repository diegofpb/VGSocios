package es.diegofpb.vgsocios.data.remote.responses

import es.diegofpb.vgsocios.data.entities.Login
import es.diegofpb.vgsocios.data.entities.MembershipInfo

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

fun MembershipInfoResponse.mapToMembershipInfo() = MembershipInfo(
    firstName = firstName,
    clubDesc = clubDesc,
    productDesc = productDesc,
    startDate = startDate
)

fun MembershipInfoResponse.mapToError() = ErrorResponse(
    error = error,
    errorCode = errorCode
)