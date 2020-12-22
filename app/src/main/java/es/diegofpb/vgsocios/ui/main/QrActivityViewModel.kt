package es.diegofpb.vgsocios.ui.main

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.util.Log
import androidx.core.view.ViewCompat
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import es.diegofpb.vgsocios.utils.Constants
import es.diegofpb.vgsocios.utils.Resource
import es.diegofpb.vgsocios.utils.Status
import kotlinx.coroutines.launch

class QrActivityViewModel @ViewModelInject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private val _qRResponse = MutableLiveData<Resource<Bitmap>>()
    val qrResponse: LiveData<Resource<Bitmap>> get() = _qRResponse

    private val _pinResponse = MutableLiveData<Resource<String>>()
    val pinResponse: LiveData<Resource<String>> get() = _pinResponse

    fun getPin() = viewModelScope.launch {
        val pin = sharedPreferences.getString(Constants.VG_USER_PIN,"")
        if (pin!!.isNotEmpty()) {
            _pinResponse.postValue(Resource.success(pin.replace("", " ").trim()))
        } else {
            _pinResponse.postValue(Resource.error("No se ha podido recuperar el PIN",
                null))
        }
    }

    fun getQR() =
        viewModelScope.launch {
            val provisCustomerId = sharedPreferences.getInt(
                Constants.VG_USER_PROVIS_CUSTOMER_ID, Int.MAX_VALUE)
            val pin = sharedPreferences.getString(
                Constants.VG_USER_PIN,"")
            Log.d("LoginViewModel-executeLogin-launch", "Enter in coroutine")
            val stringQrToEncode = "$pin,$provisCustomerId"
            val qrData = generateQR(stringQrToEncode)
            if (qrData.status == Status.SUCCESS) {
                _qRResponse.postValue(Resource.success(qrData.data))
            } else {
                _qRResponse.postValue(Resource.error(qrData.message.toString(), null))
            }
            Log.d("LoginViewModel-executeLogin-launch", "Out of coroutine")
        }


    private fun generateQR(str: String): Resource<Bitmap> {
        Log.d("QrActivityViewModel-generateQR", "Enter")
        return try {
            val encode = QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, 512, 512)
            val createBitmap =
                Bitmap.createBitmap(encode.width, encode.height, Bitmap.Config.ARGB_8888)
            for (i in 0 until createBitmap.height) {
                for (i2 in 0 until createBitmap.width) {
                    createBitmap.setPixel(
                        i,
                        i2,
                        if (encode[i, i2]) ViewCompat.MEASURED_STATE_MASK else -1
                    )
                }
            }
            Log.d("QrActivityViewModel-generateQR", "Out")
            Resource(Status.SUCCESS, createBitmap, "OK")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource(Status.ERROR, null, e.message)
        }
    }

}