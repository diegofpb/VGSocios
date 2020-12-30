package es.diegofpb.vgsocios.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import es.diegofpb.vgsocios.R
import es.diegofpb.vgsocios.ui.main.MainActivity
import es.diegofpb.vgsocios.utils.Status
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("LoginActivity", "On create")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton.setOnClickListener {
            Log.d("LoginActivity-LoginButton", "Enter - On click")
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.login(emailText.text.toString(), passwordText.text.toString())
            }
            Log.d("LoginActivity-LoginButton", "Out - On click")
        }

        passwordField.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                //Perform Code
                this.currentFocus?.hideKeyboard();
                loginButton.callOnClick()
                return@OnKeyListener true
            }
            false
        })

        viewModel.loginResponse.observe(this, {
            when (it.status) {
                Status.LOADING -> {
                    loader.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    loader.visibility = View.GONE
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    if(savePasswordSwitch.isChecked){
                        viewModel.rememberCredentials(emailText.text.toString(), passwordText.text.toString())
                    } else {
                        viewModel.dontRememberCredentials()
                    }
                    loader.visibility = View.GONE
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
        })
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}