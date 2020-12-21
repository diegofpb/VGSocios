package es.diegofpb.vgsocios.ui.booking

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        // Min and max date
        c.set(Calendar.YEAR, year)
        datePickerDialog.datePicker.minDate = c.timeInMillis
        c.set(Calendar.DAY_OF_MONTH, day + 7)
        datePickerDialog.datePicker.maxDate = c.timeInMillis
        // Create a new instance of DatePickerDialog and return it
        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
    }

}