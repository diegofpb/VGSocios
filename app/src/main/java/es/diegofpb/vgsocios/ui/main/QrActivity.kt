package es.diegofpb.vgsocios.ui.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import es.diegofpb.vgsocios.R
import es.diegofpb.vgsocios.utils.Status
import kotlinx.android.synthetic.main.activity_qr.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class QrActivity : AppCompatActivity() {

    private val qrActivityViewModel: QrActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Acceso al club"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        setFullBright()

        lifecycleScope.launch(Dispatchers.Main) {
            qrActivityViewModel.getQR()
            qrActivityViewModel.getPin()
        }

        qrActivityViewModel.qrResponse.observe(this, { it ->
            when (it.status) {
                Status.LOADING -> {
                    Log.d("QrActivity-qrResponse", "Loading")
                }
                Status.ERROR -> {
                    Log.d("QrActivity-qrResponse", "Error")
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    Log.d("QrActivity-qrResponse", "SUCCESS")
                    codigoQRImageView.setImageBitmap(it.data)
                }
            }
        })

        qrActivityViewModel.pinResponse.observe(this, { it ->
            when (it.status) {
                Status.LOADING -> {
                    Log.d("QrActivity-pinResponse", "Loading")
                }
                Status.ERROR -> {
                    Log.d("QrActivity-pinResponse", "Error")
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    Log.d("QrActivity-pinResponse", "SUCCESS")
                    codigoPinDescription.text = it.data
                }
            }
        })


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setFullBright() {
        val windowParams = window.attributes
        windowParams.screenBrightness = 1.0f
        window.attributes = windowParams
    }
}