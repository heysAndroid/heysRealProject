package com.example.heysrealprojcet.ui.channel.dialog.period

import android.graphics.Typeface
import android.view.View
import androidx.core.content.ContextCompat
import com.example.heysrealprojcet.R
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
            when (viewModel.selectedList.size) {
                in 0..1 -> {
                    viewModel.selectedList.add(day.date)
                    viewModel.updateSelectedDate(viewModel.selectedList)
                    calendarView.notifyDateChanged(day.date)
                }
                2 -> {
                    viewModel.updateSelectedDate(arrayListOf(day.date, null))
                    calendarView.notifyDateChanged(viewModel.selectedList[0]!!)
                    calendarView.notifyDateChanged(viewModel.selectedList[1]!!)
                    calendarView.notifyDateChanged(day.date)
                    viewModel.selectedList = arrayListOf(day.date)
                }
            }
        }
    }

    fun bind(day: CalendarDay) {
        this.day = day

        if(day.owner == DayOwner.THIS_MONTH) {
            bind.itemCalendarDate.text = day.date.dayOfMonth.toString()
        }

        bind.itemCalendarDate.setBackgroundResource(
            when (day.date) {
               viewModel.selectedDate.value?.get(0) -> {
                   R.drawable.bg_calendar_select_circle
               }
               viewModel.selectedDate.value?.get(1) -> {
                   R.drawable.bg_calendar_select_circle
               }
               else -> {
                   0
               }
            }
        )

        bind.itemCalendarDate.setTextColor(
            ContextCompat.getColor(
                view.context,
                when (day.date) {
                    viewModel.selectedDate.value?.get(0) -> {
                        R.color.white
                    }
                    viewModel.selectedDate.value?.get(1) -> {
                        R.color.white
                    }
                    else -> {
                        R.color.color_828282
                    }
                }
            )
        )

        bind.itemCalendarDate.setTypeface(null,
            when (day.date) {
                viewModel.selectedDate.value?.get(0) -> {
                    Typeface.BOLD
                }
                viewModel.selectedDate.value?.get(1) -> {
                    Typeface.BOLD
                }
                else -> {
                    Typeface.NORMAL
                }
            }
        )
    }
}
