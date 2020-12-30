package es.diegofpb.vgsocios.ui.splashscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import es.diegofpb.vgsocios.R
import es.diegofpb.vgsocios.ui.login.LoginActivity
import es.diegofpb.vgsocios.ui.main.MainActivity
import es.diegofpb.vgsocios.utils.Status
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val viewModel: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        if (!viewModel.credentialsStored()) {
            bg_img.animate().translationY(-4000F).setDuration(1250).startDelay = 1000
            vg_logo.animate().translationY(4000F).setDuration(1250).setStartDelay(1000)
                .withEndAction {
                    val loginActivity = Intent(this, LoginActivity::class.java)
                    startActivity(loginActivity)
                    finish()
                }
        } else {

            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.loginAttempWithToken()
            }

            viewModel.tokenResponse.observe(this, { response ->
                when (response.status) {
                    Status.LOADING -> {
                    }
                    Status.ERROR -> {
                        if (response.message == "TOKEN_NOT_VALID") {
                            Snackbar.make(window.decorView.rootView, "⌛️ Su sesión ha expirado.", 2000).show()
                            lifecycleScope.launch(Dispatchers.Main) {
                                viewModel.loginAttempWithMailAndPassword()
                            }
                        } else if (response.message == "MAIL_PASSWORD_NOT_VALID") {
                            Snackbar.make(window.decorView.rootView, "\uD83D\uDEAB Usuario o contrseña no válido", 2000).show()
                            bg_img.animate().translationY(-4000F).setDuration(1250).startDelay =
                                1000
                            vg_logo.animate().translationY(4000F).setDuration(1250)
                                .setStartDelay(1000).withEndAction {
                                    val loginActivity = Intent(this, LoginActivity::class.java)
                                    startActivity(loginActivity)
                                    finish()
                                }
                        }
                    }
                    Status.SUCCESS -> {
                        Snackbar.make(window.decorView.rootView, "\uD83D\uDC4D Sesión iniciada", 2000).show()
                        bg_img.animate().translationY(-4000F).setDuration(1250).startDelay = 1000
                        vg_logo.animate().translationY(4000F).setDuration(1250).setStartDelay(1000)
                            .withEndAction {
                                val mainActivity = Intent(this, MainActivity::class.java)
                                startActivity(mainActivity)
                                finish()
                            }
                    }
                }
            })

        }
    }
}