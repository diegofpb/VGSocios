package es.diegofpb.vgsocios

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import es.diegofpb.vgsocios.adapters.BookingAdapter
import es.diegofpb.vgsocios.adapters.NoBookingAdapter
import es.diegofpb.vgsocios.ui.booking.NewBookingActivity
import es.diegofpb.vgsocios.ui.main.MainActivity
import es.diegofpb.vgsocios.ui.main.QrActivity
import es.diegofpb.vgsocios.ui.main.fragments.ResumeViewModel
import es.diegofpb.vgsocios.utils.Status
import kotlinx.android.synthetic.main.fragment_resume.*
import kotlinx.android.synthetic.main.fragment_resume.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*

@AndroidEntryPoint
class ResumeFragment : Fragment() {

    private val resumeViewModel: ResumeViewModel by viewModels()
    private lateinit var bookingAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("ResumeFragment-onCreateView", "Inicio")

        val view = inflater.inflate(R.layout.fragment_resume, container, false)

        view.bookingRecyclerView.showShimmer()
        viewManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        lifecycleScope.launch(Dispatchers.Main) {
            resumeViewModel.getMembershipInfo()
        }

        resumeViewModel.membershipInfo.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    Log.d("ResumeFragment-membershipInfo", "Loading")
                }
                Status.ERROR -> {
                    Log.d("ResumeFragment-membershipInfo", "Error")
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    Log.d("ResumeFragment-membershipInfo", "SUCCESS")
                    helloText.text = getString(R.string.welcome_user, getGreetingMessage(), ",")
                    userName.text = it.data!!.firstName
                    clubLocation.text = it.data.clubDesc
                    productDescription.text = it.data.productDesc
                }
            }
        })

        lifecycleScope.launch(Dispatchers.Main) {
            resumeViewModel.getBookings(LocalDateTime.now(), LocalDateTime.now().plusDays(7))
        }

        resumeViewModel.bookingsInfo.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.LOADING -> {
                    Log.d("ResumeFragment-bookingsInfo", "Loading")
                }
                Status.ERROR -> {
                    Log.d("ResumeFragment-bookingsInfo", "Error")
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS -> {
                    Log.d("ResumeFragment-bookingsInfo", "SUCCESS")
                    if(it.data!!.isNotEmpty()){
                        bookingAdapter = BookingAdapter(it.data)
                        bookingRecyclerView.apply {
                            setHasFixedSize(true)
                            layoutManager = viewManager
                            bookingRecyclerView.adapter = bookingAdapter
                        }
                    } else {
                        bookingAdapter = NoBookingAdapter()
                        bookingRecyclerView.apply {
                            setHasFixedSize(true)
                            layoutManager = viewManager
                            bookingRecyclerView.adapter = bookingAdapter
                        }
                    }
                }
            }
        })

        view.fab.setOnClickListener {
            startActivity(Intent(context, NewBookingActivity::class.java))
        }

        view.qrView.setOnClickListener {
            startActivity(Intent(context, QrActivity::class.java))
        }

        Log.d("ResumeFragment-onCreateView", "End")

        return view
    }

    private fun getGreetingMessage(): String {
        return when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
            in 0..4 -> getString(R.string.good_night)
            in 5..11 -> getString(R.string.good_morning)
            in 12..15 -> getString(R.string.good_afternoon)
            in 16..20 -> getString(R.string.good_evening)
            in 21..23 -> getString(R.string.good_night)
            else -> getString(R.string.hello)
        }
    }
}