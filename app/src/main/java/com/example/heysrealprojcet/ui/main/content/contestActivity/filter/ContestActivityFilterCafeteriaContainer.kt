package com.example.heysrealprojcet.ui.main.content.contestActivity.filter

import android.graphics.Typeface
import android.view.View
import androidx.core.content.ContextCompat
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ItemCalendarDayBinding
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate

class ContestActivityFilterCafeteriaContainer(
    view: View,
    private val calendarView: CalendarView,
    private val viewModel: ContestActivityFilterViewModel
) : ViewContainer(view){

    private val bind = ItemCalendarDayBinding.bind(view)
    private lateinit var day: CalendarDay

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                if (day.date.month == LocalDate.now().month && day.date.dayOfMonth >= LocalDate.now().dayOfMonth) {
                    isSelectedDate()
                } else if (day.date.month != LocalDate.now().month) {
                    isSelectedDate()
                }
            }
        }
    }

    fun bind(day: CalendarDay) {
        this.day = day

        if(day.owner == DayOwner.THIS_MONTH) {
            bind.itemCalendarDate.text = day.date.dayOfMonth.toString()
            if(day.date.month == LocalDate.now().month && day.date.dayOfMonth < LocalDate.now().dayOfMonth) {
                bind.itemCalendarDate.setTextColor(ContextCompat.getColor(view.context, R.color.color_e1e1e1))
            } else {
                bind.itemCalendarDate.setTextColor(ContextCompat.getColor(view.context, R.color.color_828282))
            }
        }
        initDate()

        if(day.owner == DayOwner.THIS_MONTH && viewModel.selectedDate == day.date) {
            setSelectDate()
        }
    }

    private fun initDate() {
        bind.itemCalendarDate.setBackgroundResource(0)
        bind.itemCalendarDate.setTypeface(null, Typeface.NORMAL)
    }

    private fun setSelectDate() {
        bind.itemCalendarDate.setTypeface(null, Typeface.BOLD)
        bind.itemCalendarDate.setTextColor(ContextCompat.getColor(view.context, R.color.white))
        bind.itemCalendarDate.setBackgroundResource(R.drawable.bg_calendar_select_circle)
    }

    private fun isSelectedDate() {
        calendarView.notifyDateChanged(day.date)
        viewModel.selectedDate?.let { calendarView.notifyDateChanged(it) }
        viewModel.selectedDate = day.date
    }
}
