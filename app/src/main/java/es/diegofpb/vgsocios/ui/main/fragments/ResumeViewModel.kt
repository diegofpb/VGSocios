package es.diegofpb.vgsocios.ui.main.fragments

import android.content.SharedPreferences
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.diegofpb.vgsocios.data.entities.MembershipInfo
import es.diegofpb.vgsocios.data.remote.repositories.VGRepository
import es.diegofpb.vgsocios.utils.Constants
import es.diegofpb.vgsocios.utils.Resource
import es.diegofpb.vgsocios.utils.Status
import kotlinx.coroutines.launch

class ResumeViewModel @ViewModelInject constructor(
    private val repository: VGRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _membershipInfo = MutableLiveData<Resource<MembershipInfo>>()
    val membershipInfo: LiveData<Resource<MembershipInfo>> get() = _membershipInfo

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


}