package es.diegofpb.vgsocios.ui.booking

import android.content.SharedPreferences
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.diegofpb.vgsocios.data.entities.ClubInfo
import es.diegofpb.vgsocios.data.remote.repositories.VGRepository
import es.diegofpb.vgsocios.utils.Constants
import es.diegofpb.vgsocios.utils.Resource
import es.diegofpb.vgsocios.utils.Status
import kotlinx.coroutines.launch

class NewBookingViewModel @ViewModelInject constructor(
    private val repository: VGRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _clubsInfo = MutableLiveData<Resource<List<ClubInfo>>>()
    val clubsInfo: LiveData<Resource<List<ClubInfo>>> get() = _clubsInfo

    suspend fun getAvailableClubs() {
        Log.d("NewBookingViewModel-getAvailableClubs", "Enter")
        _clubsInfo.postValue(Resource.loading(null))
        viewModelScope.launch {
            val clubsInfoData = repository.getAvailableClubs(
                sharedPreferences.getInt(
                    Constants.VG_USER_CONTRACT_NO, Int.MAX_VALUE
                )
            )
            if (clubsInfoData.status == Status.SUCCESS) {
                Log.d("NewBookingViewModel-getAvailableClubs", "Success")
                _clubsInfo.postValue(Resource.success(clubsInfoData.data))
            } else {
                Log.d("NewBookingViewModel-getAvailableClubs", "Error")
                _clubsInfo.postValue(Resource.error(clubsInfoData.message.toString(), null))
            }
        }
        Log.d("NewBookingViewModel-getAvailableClubs", "Out")
    }

}