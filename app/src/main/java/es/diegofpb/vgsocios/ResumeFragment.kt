package es.diegofpb.vgsocios

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
import dagger.hilt.android.AndroidEntryPoint
import es.diegofpb.vgsocios.ui.main.fragments.ResumeViewModel
import es.diegofpb.vgsocios.utils.Status
import kotlinx.android.synthetic.main.fragment_resume.*
import kotlinx.android.synthetic.main.fragment_resume.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class ResumeFragment : Fragment() {

    private val resumeViewModel: ResumeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("ResumeFragment-onCreateView", "Inicio")

        val view = inflater.inflate(R.layout.fragment_resume, container, false)

        view.bookingRecyclerView.showShimmer()

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