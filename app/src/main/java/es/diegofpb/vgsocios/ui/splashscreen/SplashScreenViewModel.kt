package es.diegofpb.vgsocios.ui.splashscreen

import android.content.SharedPreferences
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.diegofpb.vgsocios.data.remote.repositories.VGRepository
import es.diegofpb.vgsocios.ui.login.LoginViewModel
import es.diegofpb.vgsocios.utils.Constants
import es.diegofpb.vgsocios.utils.Constants.VG_USER_CONTRACT_PERSON_ID
import es.diegofpb.vgsocios.utils.Constants.VG_USER_MAIL
import es.diegofpb.vgsocios.utils.Constants.VG_USER_PASS
import es.diegofpb.vgsocios.utils.Constants.VG_USER_REMEMBER_ME
import es.diegofpb.vgsocios.utils.Constants.VG_USER_TOKEN
import es.diegofpb.vgsocios.utils.Resource
import es.diegofpb.vgsocios.utils.Status
import kotlinx.coroutines.launch

class SplashScreenViewModel @ViewModelInject constructor(
    private val repository: VGRepository,
    private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val _validTokenResponse = MutableLiveData<Resource<String>>()
    val tokenResponse: LiveData<Resource<String>> get() = _validTokenResponse

    fun credentialsStored() = sharedPreferences.getBoolean(VG_USER_REMEMBER_ME, false)


    suspend fun loginAttempWithToken() =
        viewModelScope.launch {
            Log.d("LoginViewModel-loginAttempWithToken", "Enter in coroutine")
            val userToken = sharedPreferences.getString(VG_USER_TOKEN, "")
            repository.saveToken()
            val contractId = sharedPreferences.getInt(VG_USER_CONTRACT_PERSON_ID, Int.MAX_VALUE)
            if (userToken!!.isNotEmpty() && contractId != Int.MAX_VALUE) {
                val membershipInfo = repository.getViewMembershipInfo(contractId)
                if (membershipInfo.status == Status.SUCCESS) {
                    _validTokenResponse.postValue(Resource.success(userToken))
                } else {
                    _validTokenResponse.postValue(Resource.error("TOKEN_NOT_VALID",null))
                    sharedPreferences.edit().putString(VG_USER_TOKEN, null).apply()
                }
            }
            Log.d("LoginViewModel-loginAttempWithToken-launch", "Out of coroutine")
        }

    suspend fun loginAttempWithMailAndPassword() =
        viewModelScope.launch {
            Log.d("LoginViewModel-loginAttempWithMailAndPassword", "Enter in coroutine")
            val loginData = repository.login(sharedPreferences.getString(VG_USER_MAIL, "")!!,
                sharedPreferences.getString(VG_USER_PASS, "")!!)
            if (loginData.status == Status.SUCCESS) {
                sharedPreferences.edit().putString(VG_USER_TOKEN, loginData.data!!.token).apply()
                repository.saveToken()
                sharedPreferences.edit().putInt(VG_USER_CONTRACT_PERSON_ID, loginData.data.contractPersonId).apply()
                sharedPreferences.edit().putInt(Constants.VG_USER_CONTRACT_NO, loginData.data.contractNo).apply()
                sharedPreferences.edit().putInt(Constants.VG_USER_CLUB_NO, loginData.data.clubNo).apply()
                _validTokenResponse.postValue(Resource.success(loginData.data.token))
            } else {
                _validTokenResponse.postValue(Resource.error("MAIL_PASSWORD_NOT_VALID", null))
            }
            Log.d("LoginViewModel-loginAttempWithMailAndPassword-launch", "Out of coroutine")
        }


}
