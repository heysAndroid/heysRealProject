package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.filter

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ContestExtracurricularFilterFragmentBinding
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

class ContestExtracurricularFilterFragment : Fragment() {
   private lateinit var binding: ContestExtracurricularFilterFragmentBinding
   private lateinit var viewmodel: ContestExtracurricularFilterViewModel

   private var currentTime = YearMonth.now()
   private val calendarFormatter = DateTimeFormatter.ofPattern("yyyy년 M월")
   private val selectedFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ContestExtracurricularFilterFragmentBinding.inflate(inflater, container, false)
      viewmodel = ContestExtracurricularFilterViewModel(binding.cafeteriaCalendar)
      binding.vm = viewmodel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      binding.btnApply.setOnClickListener { findNavController().navigateUp() }

      viewmodel.selectedDate
      viewmodel.calendarPosition.observe(viewLifecycleOwner) {
         if (currentTime.year == YearMonth.now().year && it == YearMonth.now().month.value) {
            binding.calendarBack.visibility = View.INVISIBLE
         } else {
            binding.calendarBack.visibility = View.VISIBLE
         }
      }
      viewmodel.calendarDate.observe(viewLifecycleOwner) {
         if (viewmodel.selectedDate != null) {
            binding.tvDate.text = viewmodel.calendarDate.value!!.format(selectedFormatter)
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
            if (viewmodel.calendarPosition.value == 1) {
               viewmodel.setPosition(12)
               currentTime = currentTime.minusYears(1)
               currentTime = currentTime.withMonth(viewmodel.calendarPosition.value!!)
               binding.cafeteriaCalendar.setup(
                  currentTime.withMonth(viewmodel.calendarPosition.value!!),
                  currentTime.withMonth(viewmodel.calendarPosition.value!!),
                  WeekFields.of(Locale.getDefault()).firstDayOfWeek
               )
            } else {
               viewmodel.minusPosition()
               binding.cafeteriaCalendar.setup(
                  currentTime.withMonth(viewmodel.calendarPosition.value!!),
                  currentTime.withMonth(viewmodel.calendarPosition.value!!),
                  WeekFields.of(Locale.getDefault()).firstDayOfWeek
               )
               currentTime = currentTime.withMonth(viewmodel.calendarPosition.value!!)
            }
         } else {
            if (currentTime.year == YearMonth.now().year &&
               viewmodel.calendarPosition.value!! > YearMonth.now().month.value
            ) {
               viewmodel.minusPosition()
               binding.cafeteriaCalendar.setup(
                  currentTime.withMonth(viewmodel.calendarPosition.value!!),
                  currentTime.withMonth(viewmodel.calendarPosition.value!!),
                  WeekFields.of(Locale.getDefault()).firstDayOfWeek
               )
               currentTime = currentTime.withMonth(viewmodel.calendarPosition.value!!)
            }
         }
         binding.yearMonth.text = currentTime.format(calendarFormatter)
      }

      binding.calendarForward.setOnClickListener {
         if (viewmodel.calendarPosition.value in 1..11) {
            currentTime = currentTime.withMonth(viewmodel.calendarPosition.value!!)
            viewmodel.plusPosition()
            binding.cafeteriaCalendar.setup(
               currentTime.withMonth(viewmodel.calendarPosition.value!!),
               currentTime.withMonth(viewmodel.calendarPosition.value!!),
               WeekFields.of(Locale.getDefault()).firstDayOfWeek
            )
            currentTime = currentTime.plusMonths(1)
         } else {
            viewmodel.setPosition(1)
            currentTime = currentTime.plusYears(1)
            currentTime = currentTime.withMonth(viewmodel.calendarPosition.value!!)
            binding.cafeteriaCalendar.setup(
               currentTime.withMonth(viewmodel.calendarPosition.value!!),
               currentTime.withMonth(viewmodel.calendarPosition.value!!),
               WeekFields.of(Locale.getDefault()).firstDayOfWeek
            )
         }
         binding.yearMonth.text = currentTime.format(calendarFormatter)
      }

      binding.cafeteriaCalendar.dayBinder = object : DayBinder<ContestExtracurricularFilterCafeteriaContainer> {
         override fun create(view: View): ContestExtracurricularFilterCafeteriaContainer =
            ContestExtracurricularFilterCafeteriaContainer(view, binding.cafeteriaCalendar, viewmodel)

         override fun bind(container: ContestExtracurricularFilterCafeteriaContainer, day: CalendarDay) = container.bind(day)
      }
   }
}