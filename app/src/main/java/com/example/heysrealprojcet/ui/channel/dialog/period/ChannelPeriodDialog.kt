package com.example.heysrealprojcet.ui.channel.dialog.period

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.heysrealprojcet.databinding.ChannelPeriodDialogBinding
import com.example.heysrealprojcet.util.ChannelPreference
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*


class ChannelPeriodDialog : DialogFragment() {
   private lateinit var binding: ChannelPeriodDialogBinding
   private val viewModel by viewModels<ChannelPeriodDialogViewModel>()
   private lateinit var listener: ChannelPeriodDialogOnClickListener

   private var currentTime = YearMonth.now()
   private val formatter = DateTimeFormatter.ofPattern("yyyy년 M월")
   private var calendarPosition = 1

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelPeriodDialogBinding.inflate(inflater, container, false)
      binding.vm = viewModel

      dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
      dialog?.setCancelable(false)

      binding.cafeteriaCalendar.itemAnimator = null

      binding.cafeteriaCalendar.setup(
         YearMonth.now(),
         YearMonth.now(),
         WeekFields.of(Locale.getDefault()).firstDayOfWeek
      )
      binding.yearMonth.text = currentTime.format(formatter)

      binding.calendarBack.setOnClickListener {
         if(currentTime.year > YearMonth.now().year) {
            if(calendarPosition == 1) {
               calendarPosition = 12
               currentTime = currentTime.minusYears(1)
               currentTime = currentTime.withMonth(calendarPosition)
               binding.cafeteriaCalendar.setup(
                  currentTime.withMonth(calendarPosition),
                  currentTime.withMonth(calendarPosition),
                  WeekFields.of(Locale.getDefault()).firstDayOfWeek
               )
            } else {
               calendarPosition -= 1
               binding.cafeteriaCalendar.setup(
                  currentTime.withMonth(calendarPosition),
                  currentTime.withMonth(calendarPosition),
                  WeekFields.of(Locale.getDefault()).firstDayOfWeek
               )
               currentTime = currentTime.withMonth(calendarPosition)
            }
         }
         else {
            if(currentTime.year == YearMonth.now().year && calendarPosition > YearMonth.now().month.value) {
               calendarPosition -= 1
               binding.cafeteriaCalendar.setup(
                  currentTime.withMonth(calendarPosition),
                  currentTime.withMonth(calendarPosition),
                  WeekFields.of(Locale.getDefault()).firstDayOfWeek
               )
               currentTime = currentTime.withMonth(calendarPosition)
            }
         }
         binding.yearMonth.text = currentTime.format(formatter)
      }


      binding.calendarForward.setOnClickListener {
         if(calendarPosition in 1..12) {
            currentTime = currentTime.withMonth(calendarPosition)
            binding.cafeteriaCalendar.setup(
               currentTime.withMonth(calendarPosition),
               currentTime.withMonth(calendarPosition),
               WeekFields.of(Locale.getDefault()).firstDayOfWeek
            )
            calendarPosition += 1
         }
         else {
            calendarPosition = 1
            currentTime = currentTime.plusYears(1)
            currentTime = currentTime.withMonth(calendarPosition)
            binding.cafeteriaCalendar.setup(
               currentTime.withMonth(calendarPosition),
               currentTime.withMonth(calendarPosition),
               WeekFields.of(Locale.getDefault()).firstDayOfWeek
            )
         }
         binding.yearMonth.text = currentTime.format(formatter)
      }

      binding.cafeteriaCalendar.dayBinder = object : DayBinder<CafeteriaContainer> {
         override fun create(view: View): CafeteriaContainer =
            CafeteriaContainer(view, binding.cafeteriaCalendar, viewModel)

         override fun bind(container: CafeteriaContainer, day: CalendarDay) = container.bind(day)
      }

      binding.btnSave.setOnClickListener {
         // 채널 정보 fragment 로 선택값 전달
         listener.onClick(ChannelPreference.channelRecruitPeriod)
         dialog?.dismiss()
      }
      binding.closeButton.setOnClickListener { dismiss() }

      return binding.root
   }

   fun setOnOKClickListener(listener: (String) -> Unit) {
      this.listener = object : ChannelPeriodDialogOnClickListener {
         override fun onClick(content: String) {
            listener(content)
         }
      }
   }

   interface ChannelPeriodDialogOnClickListener {
      fun onClick(content: String)
   }
}