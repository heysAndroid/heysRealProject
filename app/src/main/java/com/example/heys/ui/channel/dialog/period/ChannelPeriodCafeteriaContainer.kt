package com.example.heys.ui.channel.dialog.period

import android.graphics.Typeface
import android.view.View
import androidx.core.content.ContextCompat
import com.example.heys.R
import com.example.heys.databinding.ItemCalendarDayBinding
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.LocalDate

class ChannelPeriodCafeteriaContainer(
    view: View,
    private val calendarView: CalendarView,
    private val viewModel: ChannelPeriodDialogViewModel
) : ViewContainer(view){

    private val bind = ItemCalendarDayBinding.bind(view)
    private lateinit var day: CalendarDay

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                if(day.date.month == LocalDate.now().month && day.date.dayOfMonth >= LocalDate.now().dayOfMonth) {
                    isSelectedDate()
                } else if(day.date.month != LocalDate.now().month) {
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

        for(i in 0 until (viewModel.selectedDate.value?.size?: 0)) {
            if(day.owner == DayOwner.THIS_MONTH && day.date == viewModel.selectedDate.value?.get(i)) {
                setSelectDate()
            }
        }

        if(day.owner == DayOwner.THIS_MONTH && viewModel.selectedDate.value?.size == 2) {
            if(isWithinRange(day.date)) {
                setMiddleDate()
            }
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

    private fun setMiddleDate() {
        bind.itemCalendarDate.setTypeface(null, Typeface.BOLD)
        bind.itemCalendarDate.setTextColor(ContextCompat.getColor(view.context, R.color.color_53c740))
        bind.itemCalendarDate.setBackgroundResource(R.drawable.bg_calendar_middle_circle)
    }

    private fun isWithinRange(date: LocalDate): Boolean {
        return date.isAfter(viewModel.selectedDate.value!![0])
                && date.isBefore(viewModel.selectedDate.value!![1])
    }

    private fun isSelectedDate() {
        when (viewModel.selectedList.size) {
            0 -> {
                viewModel.selectedList.add(day.date)
                viewModel.updateSelectedDate(viewModel.selectedList)
                calendarView.notifyDateChanged(day.date)
            }
            1 -> {
                viewModel.selectedList.add(day.date)
                viewModel.selectedList.sort()
                viewModel.updateSelectedDate(viewModel.selectedList)
                calendarView.notifyCalendarChanged()
            }
            2 -> {
                viewModel.updateSelectedDate(arrayListOf(day.date))
                calendarView.notifyCalendarChanged()
                viewModel.selectedList = arrayListOf(day.date)
            }
        }
    }
}
