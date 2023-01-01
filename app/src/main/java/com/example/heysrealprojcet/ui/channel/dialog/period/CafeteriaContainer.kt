package com.example.heysrealprojcet.ui.channel.dialog.period

import android.view.View
import com.example.heysrealprojcet.databinding.ItemCalendarDayBinding
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer

class CafeteriaContainer(
    view: View,
    private val calendarView: CalendarView,
    private val viewModel: ChannelPeriodDialogViewModel
) : ViewContainer(view){

    private val bind = ItemCalendarDayBinding.bind(view)
    private lateinit var day: CalendarDay

    init {
        view.setOnClickListener {

        }
    }

    fun bind(day: CalendarDay) {
        this.day = day

        if(day.owner == DayOwner.THIS_MONTH) {
            bind.itemCalendarDate.text = day.date.dayOfMonth.toString()
        }
    }
}
