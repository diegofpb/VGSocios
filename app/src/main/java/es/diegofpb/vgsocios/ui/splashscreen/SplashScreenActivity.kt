package es.diegofpb.vgsocios.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import es.diegofpb.vgsocios.R
import es.diegofpb.vgsocios.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        bg_img.animate().translationY(-4000F).setDuration(1500).startDelay = 2000
        vg_logo.animate().translationY(4000F).setDuration(1500).setStartDelay(2000).withEndAction {
            val loginActivity = Intent(this, LoginActivity::class.java)
            startActivity(loginActivity)
        }


    }
}