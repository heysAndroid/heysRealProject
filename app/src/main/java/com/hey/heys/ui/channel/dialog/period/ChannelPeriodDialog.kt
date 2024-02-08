package com.hey.heys.ui.channel.dialog.period

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.hey.heys.databinding.ChannelPeriodDialogBinding
import com.hey.heys.util.ChannelPreference
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

   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      binding = ChannelPeriodDialogBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      binding.lifecycleOwner = this

      dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
      dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
      dialog?.setCancelable(false)

      viewModel.calendarPosition.observe(viewLifecycleOwner) {
         if(currentTime.year == YearMonth.now().year && it == YearMonth.now().month.value) {
            binding.calendarBack.visibility = View.INVISIBLE
         } else {
            binding.calendarBack.visibility = View.VISIBLE
         }
      }

      binding.cafeteriaCalendar.itemAnimator = null

      binding.cafeteriaCalendar.setup(
         YearMonth.now(),
         YearMonth.now(),
         WeekFields.of(Locale.getDefault()).firstDayOfWeek
      )
      binding.yearMonth.text = currentTime.format(formatter)

      binding.calendarBack.setOnClickListener {
         if (currentTime.year > YearMonth.now().year) {
            if (viewModel.calendarPosition.value == 1) {
               viewModel.setPosition(12)
               currentTime = currentTime.minusYears(1)
               currentTime = currentTime.withMonth(viewModel.calendarPosition.value!!)
               binding.cafeteriaCalendar.setup(
                  currentTime.withMonth(viewModel.calendarPosition.value!!),
                  currentTime.withMonth(viewModel.calendarPosition.value!!),
                  WeekFields.of(Locale.getDefault()).firstDayOfWeek
               )
            } else {
               viewModel.minusPosition()
               binding.cafeteriaCalendar.setup(
                  currentTime.withMonth(viewModel.calendarPosition.value!!),
                  currentTime.withMonth(viewModel.calendarPosition.value!!),
                  WeekFields.of(Locale.getDefault()).firstDayOfWeek
               )
               currentTime = currentTime.withMonth(viewModel.calendarPosition.value!!)
            }
         } else {
            if (currentTime.year == YearMonth.now().year && viewModel.calendarPosition.value!! > YearMonth.now().month.value) {
               viewModel.minusPosition()
               binding.cafeteriaCalendar.setup(
                  currentTime.withMonth(viewModel.calendarPosition.value!!),
                  currentTime.withMonth(viewModel.calendarPosition.value!!),
                  WeekFields.of(Locale.getDefault()).firstDayOfWeek
               )
               currentTime = currentTime.withMonth(viewModel.calendarPosition.value!!)
            }
         }
         binding.yearMonth.text = currentTime.format(formatter)
      }

      binding.calendarForward.setOnClickListener {
         if (viewModel.calendarPosition.value in 1..11) {
            currentTime = currentTime.withMonth(viewModel.calendarPosition.value!!)
            viewModel.plusPosition()
            binding.cafeteriaCalendar.setup(
               currentTime.withMonth(viewModel.calendarPosition.value!!),
               currentTime.withMonth(viewModel.calendarPosition.value!!),
               WeekFields.of(Locale.getDefault()).firstDayOfWeek
            )
            currentTime = currentTime.plusMonths(1)
         } else {
            viewModel.setPosition(1)
            currentTime = currentTime.plusYears(1)
            currentTime = currentTime.withMonth(viewModel.calendarPosition.value!!)
            binding.cafeteriaCalendar.setup(
               currentTime.withMonth(viewModel.calendarPosition.value!!),
               currentTime.withMonth(viewModel.calendarPosition.value!!),
               WeekFields.of(Locale.getDefault()).firstDayOfWeek
            )
         }
         binding.yearMonth.text = currentTime.format(formatter)
      }

      binding.cafeteriaCalendar.dayBinder = object : DayBinder<ChannelPeriodCafeteriaContainer> {
         override fun create(view: View): ChannelPeriodCafeteriaContainer =
            ChannelPeriodCafeteriaContainer(view, binding.cafeteriaCalendar, viewModel)

         override fun bind(container: ChannelPeriodCafeteriaContainer, day: CalendarDay) = container.bind(day)
      }

      viewModel.selectedDate.observe(viewLifecycleOwner) {
         if (it.size == 2) viewModel.onEnabled()
         else viewModel.unEnabled()
      }

      binding.btnSave.setOnClickListener {
         // 채널 정보 fragment 로 선택값 전달
         listener.onClick(ChannelPreference.channelRecruitEndDay)
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