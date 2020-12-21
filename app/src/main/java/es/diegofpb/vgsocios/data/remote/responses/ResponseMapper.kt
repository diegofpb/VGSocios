package es.diegofpb.vgsocios.data.remote.responses

import es.diegofpb.vgsocios.data.entities.BookingInfo
import es.diegofpb.vgsocios.data.entities.ClubInfo
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

fun BookingResponse.mapToBookingInfo() = BookingInfo(
    classTypeName = classTypeName,
    clubDesc = clubDesc,
    displayDate = displayDate,
    bookingDate = bookingDate,
    clubClassId = clubClassId,
    isTimeBased = isTimeBased
)

fun Array<BookingResponse>.mapToError() = ErrorResponse(
    error = "No ha sido posible deserializar el Array",
    errorCode = 9999
)

fun ClubListForContractIdResponse.mapToClubInfo() = ClubInfo(
    clubName = clubName,
    clubNo = clubNo
)

fun Array<ClubListForContractIdResponse>.mapToError() = ErrorResponse(
    error = "No ha sido posible deserializar el Array",
    errorCode = 9999
)