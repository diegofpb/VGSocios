package es.diegofpb.vgsocios.ui.login

import android.content.SharedPreferences
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.diegofpb.vgsocios.data.entities.Login
import es.diegofpb.vgsocios.data.remote.repositories.VGRepository
import es.diegofpb.vgsocios.utils.Constants.VG_USER_CLUB_NO
import es.diegofpb.vgsocios.utils.Constants.VG_USER_CONTRACT_NO
import es.diegofpb.vgsocios.utils.Constants.VG_USER_CONTRACT_PERSON_ID
import es.diegofpb.vgsocios.utils.Constants.VG_USER_MAIL
import es.diegofpb.vgsocios.utils.Constants.VG_USER_PASS
import es.diegofpb.vgsocios.utils.Constants.VG_USER_REMEMBER_ME
import es.diegofpb.vgsocios.utils.Constants.VG_USER_TOKEN
import es.diegofpb.vgsocios.utils.Resource
import es.diegofpb.vgsocios.utils.Status
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    private val repository: VGRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _loginResponse = MutableLiveData<Resource<Login>>()
    val loginResponse: LiveData<Resource<Login>> get() = _loginResponse

    suspend fun login(username: String?, password: String?) {
        _loginResponse.postValue(Resource.loading(null))
        when {
            username.isNullOrEmpty() -> {
                Log.d("LoginViewModel-login", "Username Null Or Empty")
                _loginResponse.postValue(Resource.error("Username Null Or Empty", null))
            }
            password.isNullOrEmpty() -> {
                Log.d("LoginViewModel-login", "Password Null Or Empty")
                _loginResponse.postValue(Resource.error("Password Null Or Empty", null))
            }
            else -> {
                Log.d("LoginViewModel-login", "Otherwise")
                executeLogin(username, password)
                Log.d("LoginViewModel-login", "")
            }
        }
    }

    private suspend fun executeLogin(username: String, password: String) =
        viewModelScope.launch {
            Log.d("LoginViewModel-executeLogin-launch", "Enter in coroutine")
            val loginData = repository.login(username, password)
            if (loginData.status == Status.SUCCESS) {
                _loginResponse.postValue(Resource.success(loginData.data))
                sharedPreferences.edit().putString(VG_USER_TOKEN, loginData.data!!.token).apply()
                repository.saveToken()
                sharedPreferences.edit().putInt(VG_USER_CONTRACT_PERSON_ID, loginData.data.contractPersonId).apply()
                sharedPreferences.edit().putInt(VG_USER_CONTRACT_NO, loginData.data.contractNo).apply()
                sharedPreferences.edit().putInt(VG_USER_CLUB_NO, loginData.data.clubNo).apply()
            } else {
                _loginResponse.postValue(Resource.error(loginData.message.toString(), null))
            }
            Log.d("LoginViewModel-executeLogin-launch", "Out of coroutine")
        }

    fun rememberCredentials(username: String, password: String){
        sharedPreferences.edit().putBoolean(VG_USER_REMEMBER_ME, true).apply()
        sharedPreferences.edit().putString(VG_USER_MAIL, username).apply()
        sharedPreferences.edit().putString(VG_USER_PASS, password).apply()
    }

    fun dontRememberCredentials() {
        sharedPreferences.edit().putBoolean(VG_USER_REMEMBER_ME, false).apply()
        sharedPreferences.edit().remove(VG_USER_MAIL).apply()
        sharedPreferences.edit().remove(VG_USER_PASS).apply()
    }
}