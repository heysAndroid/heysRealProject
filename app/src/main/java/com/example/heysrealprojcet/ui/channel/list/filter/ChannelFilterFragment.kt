package com.example.heysrealprojcet.ui.channel.list.filter

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.EventObserver
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ChannelFilterFragmentBinding
import com.example.heysrealprojcet.enums.ChannelForm
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

class ChannelFilterFragment : Fragment() {
   private lateinit var binding: ChannelFilterFragmentBinding
   private val viewModel: ChannelFilterViewModel by viewModels()

   private var currentTime = YearMonth.now()
   private val calendarFormatter = DateTimeFormatter.ofPattern("yyyy년 M월")
   private val selectedFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ChannelFilterFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.cafeteriaCalendar.stopScroll()
      binding.cafeteriaCalendar.isNestedScrollingEnabled = false

      binding.btnApply.setOnClickListener { findNavController().navigateUp() }

      viewModel.selectedForm.observe(viewLifecycleOwner) {
         when (it) {
            ChannelForm.Offline.form -> {
               binding.regionContainer.visibility = View.VISIBLE
            }

            ChannelForm.Online.form -> {
               binding.regionContainer.visibility = View.GONE
            }

            else -> {
               binding.regionContainer.visibility = View.GONE
            }
         }
      }

      // 달력
      viewModel.calendarPosition.observe(viewLifecycleOwner)
      {
         if (currentTime.year == YearMonth.now().year && it == YearMonth.now().month.value) {
            binding.calendarBack.visibility = View.INVISIBLE
         } else {
            binding.calendarBack.visibility = View.VISIBLE
         }
      }

      viewModel.calendarDate.observe(viewLifecycleOwner)
      {
         if (viewModel.selectedDate != null) {
            binding.tvDate.text = viewModel.calendarDate.value!!.format(selectedFormatter)
            binding.tvDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_53c740))
            binding.tvDate.setTypeface(null, Typeface.BOLD)
         } else {
            binding.tvDate.text = "선택해주세요"
            binding.tvDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_828282))
            binding.tvDate.setTypeface(null, Typeface.NORMAL)
         }
      }

      binding.cafeteriaCalendar.itemAnimator = null
      binding.cafeteriaCalendar.setup(
         YearMonth.now(),
         YearMonth.now(),
         WeekFields.of(Locale.getDefault()).firstDayOfWeek
      )

      binding.yearMonth.text = currentTime.format(calendarFormatter)
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
            if (currentTime.year == YearMonth.now().year &&
               viewModel.calendarPosition.value!! > YearMonth.now().month.value
            ) {
               viewModel.minusPosition()
               binding.cafeteriaCalendar.setup(
                  currentTime.withMonth(viewModel.calendarPosition.value!!),
                  currentTime.withMonth(viewModel.calendarPosition.value!!),
                  WeekFields.of(Locale.getDefault()).firstDayOfWeek
               )
               currentTime = currentTime.withMonth(viewModel.calendarPosition.value!!)
            }
         }
         binding.yearMonth.text = currentTime.format(calendarFormatter)
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
         binding.yearMonth.text = currentTime.format(calendarFormatter)
      }

      binding.cafeteriaCalendar.dayBinder = object : DayBinder<ChannelFilterCafeteriaContainer> {
         override fun create(view: View): ChannelFilterCafeteriaContainer =
            ChannelFilterCafeteriaContainer(view, binding.cafeteriaCalendar, viewModel)

         override fun bind(container: ChannelFilterCafeteriaContainer, day: CalendarDay) = container.bind(day)
      }

      viewModel.isCalendarInit.observe(viewLifecycleOwner, EventObserver {
         if (it) binding.cafeteriaCalendar.notifyCalendarChanged()
      })
   }
}