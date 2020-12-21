package es.diegofpb.vgsocios.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import es.diegofpb.vgsocios.R

class NoBookingAdapter() :
        RecyclerView.Adapter<NoBookingAdapter.MyViewHolder>() {

    private var context: Context? = null

    class MyViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.booking_card_no_bookings, parent, false) as CardView
        this.context = parent.context;
        return MyViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        /*holder.cardView.setOnClickListener {
            val addBookingActivityIntent = Intent(this.context, AddBookingActivity::class.java)
            this.context?.let { it1 -> startActivity(it1, addBookingActivityIntent, null) }
        }*/

    }

    override fun getItemCount(): Int = 1

}

