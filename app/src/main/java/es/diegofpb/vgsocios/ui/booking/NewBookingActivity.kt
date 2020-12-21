package es.diegofpb.vgsocios.ui.booking

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import es.diegofpb.vgsocios.R
import es.diegofpb.vgsocios.utils.Status
import kotlinx.android.synthetic.main.activity_new_booking.*
import kotlinx.android.synthetic.main.fragment_resume.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewBookingActivity : AppCompatActivity() {

    private val newBookingViewModel: NewBookingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("NewBookingActivity", "On create")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_booking)

        val menu = PopupMenu(this, window.decorView.rootView)

        lifecycleScope.launch(Dispatchers.Main) {
            newBookingViewModel.getAvailableClubs()
        }

        newBookingViewModel.clubsInfo.observe(this, { it ->
            when (it.status) {
                Status.LOADING -> {
                    Log.d("NewBookingActivity-clubsInfo", "Loading")
                }
                Status.ERROR -> {
                    Log.d("NewBookingActivity-clubsInfo", "Error")
                    Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    Log.d("NewBookingActivity-clubsInfo", "SUCCESS")
                    var i = Menu.NONE
                    it.data!!.forEach {club ->
                        menu.menu.add(Menu.NONE, club.clubNo!!, i, club.clubName)
                        i++
                    }
                }
            }
        })


        clubSelectorEditText.setOnClickListener {
            menu.show()
        }

        clubSelector.setOnClickListener{
            menu.show()
        }

        dateSelectorEditText.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val newFragment = DatePickerFragment()
        newFragment.show(supportFragmentManager, "datePicker")
    }
}