package es.diegofpb.vgsocios.ui.main.fragments

import android.content.SharedPreferences
import android.util.Log
import android.util.Xml
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.diegofpb.vgsocios.data.entities.BookingInfo
import es.diegofpb.vgsocios.data.entities.MembershipInfo
import es.diegofpb.vgsocios.data.remote.repositories.VGRepository
import es.diegofpb.vgsocios.utils.Constants
import es.diegofpb.vgsocios.utils.Resource
import es.diegofpb.vgsocios.utils.Status
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.time.LocalDateTime
import java.time.ZoneId

class ResumeViewModel @ViewModelInject constructor(
    private val repository: VGRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _membershipInfo = MutableLiveData<Resource<MembershipInfo>>()
    val membershipInfo: LiveData<Resource<MembershipInfo>> get() = _membershipInfo

    private val _bookingsInfo = MutableLiveData<Resource<List<BookingInfo>>>()
    val bookingsInfo: LiveData<Resource<List<BookingInfo>>> get() = _bookingsInfo

    suspend fun getMembershipInfo() {
        Log.d("ResumeViewModel-getMembershipInfo", "Enter")
        _membershipInfo.postValue(Resource.loading(null))
        viewModelScope.launch {
            val membershipInfoData = repository.getViewMembershipInfo(sharedPreferences.getInt(
                Constants.VG_USER_CONTRACT_PERSON_ID, Int.MAX_VALUE))
            if (membershipInfoData.status == Status.SUCCESS) {
                Log.d("ResumeViewModel-getMembershipInfo", "Success")
                _membershipInfo.postValue(Resource.success(membershipInfoData.data))
            } else {
                Log.d("ResumeViewModel-getMembershipInfo", "Error")
                _membershipInfo.postValue(Resource.error(membershipInfoData.message.toString(), null))
            }
        }
        Log.d("ResumeViewModel-getMembershipInfo", "Out")
    }


    suspend fun getBookings(fromDate: LocalDateTime, toDate: LocalDateTime) {
        Log.d("ResumeViewModel-getBookings", "Enter")
        val fromDateEncoded = URLEncoder.encode(fromDate.toString() + fromDate.atZone(ZoneId.systemDefault()).offset, Xml.Encoding.UTF_8.name)
        val toDateEncoded = URLEncoder.encode(toDate.toString() + toDate.atZone(ZoneId.systemDefault()).offset, Xml.Encoding.UTF_8.name)
        _bookingsInfo.postValue(Resource.loading(null))
        viewModelScope.launch {
            val bookingsInfoData = repository.getBookings(sharedPreferences.getInt(
                Constants.VG_USER_CONTRACT_PERSON_ID, Int.MAX_VALUE),fromDateEncoded, toDateEncoded)
            if (bookingsInfoData.status == Status.SUCCESS) {
                Log.d("ResumeViewModel-getBookings", "Success")
                _bookingsInfo.postValue(Resource.success(bookingsInfoData.data))
            } else {
                Log.d("ResumeViewModel-getBookings", "Error")
                _bookingsInfo.postValue(Resource.error(bookingsInfoData.message.toString(), null))
            }
        }
        Log.d("ResumeViewModel-getBookings", "Out")
    }
}