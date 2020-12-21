package es.diegofpb.vgsocios.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import es.diegofpb.vgsocios.R
import es.diegofpb.vgsocios.data.entities.BookingInfo
import kotlinx.android.synthetic.main.booking_card_view.view.*
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class BookingAdapter(private val bookingList: List<BookingInfo>) :
        RecyclerView.Adapter<BookingAdapter.MyViewHolder>() {

    private var context: Context? = null

    class MyViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val cardView = LayoutInflater.from(parent.context)
                .inflate(R.layout.booking_card_view, parent, false) as CardView
        this.context = parent.context;
        return MyViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cardView.bookingTitle.text = bookingList[position].classTypeName?.toLowerCase(Locale.getDefault())?.capitalizeWords()
        holder.cardView.bookingClub.text = bookingList[position].clubDesc
        holder.cardView.bookingDate.text = bookingList[position].bookingDate?.let { getDateString(it) }
        /*holder.cardView.setOnClickListener {
            val userActivityIntent = Intent(this.context, BookingDetailActivity::class.java).apply {
                putExtra("clubClassId", bookingList[position].clubClassId)
            }
            this.context?.let { it1 -> startActivity(it1, userActivityIntent, null) }
        }*/

    }

    override fun getItemCount(): Int = bookingList.size

    private fun getDateString(date: String): String {
        var returnDate: String = date
        try {
            val parsedDate = LocalDateTime.parse(date, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val zdt: ZonedDateTime = parsedDate.atZone(ZoneId.systemDefault())
            val millis = zdt.toInstant().toEpochMilli()

            returnDate = DateUtils.getRelativeDateTimeString(context, millis,
                    DateUtils.DAY_IN_MILLIS,
                    DateUtils.WEEK_IN_MILLIS, 0).toString()

        } catch (exception: Exception) {
            return returnDate
        }
        return returnDate
    }

    @SuppressLint("DefaultLocale")
    fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

}

