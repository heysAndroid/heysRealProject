package com.example.heysrealprojcet.ui.main.content.contestExtracurricular.filter

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.EventObserver
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.ContestExtracurricularFilterFragmentBinding
import com.example.heysrealprojcet.enums.ChannelInterest
import com.example.heysrealprojcet.ui.main.MainFragment.Companion.MY_INTEREST_LIST
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

class ContestExtracurricularFilterFragment : Fragment() {
   private lateinit var binding: ContestExtracurricularFilterFragmentBinding
   private val viewModel: ContestExtracurricularFilterViewModel by viewModels()

   private var currentTime = YearMonth.now()
   private val calendarFormatter = DateTimeFormatter.ofPattern("yyyy년 M월")
   private val selectedFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ContestExtracurricularFilterFragmentBinding.inflate(inflater, container, false)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      initStartView()

      binding.cafeteriaCalendar.stopScroll()
      binding.cafeteriaCalendar.isNestedScrollingEnabled = false

      binding.btnApply.setOnClickListener { findNavController().navigateUp() }

      viewModel.calendarPosition.observe(viewLifecycleOwner) {
         if (currentTime.year == YearMonth.now().year && it == YearMonth.now().month.value) {
            binding.calendarBack.visibility = View.INVISIBLE
         } else {
            binding.calendarBack.visibility = View.VISIBLE
         }
      }

      viewModel.calendarDate.observe(viewLifecycleOwner) {
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

      binding.cafeteriaCalendar.dayBinder = object : DayBinder<ContestExtracurricularFilterCafeteriaContainer> {
         override fun create(view: View): ContestExtracurricularFilterCafeteriaContainer =
            ContestExtracurricularFilterCafeteriaContainer(view, binding.cafeteriaCalendar, viewModel)

         override fun bind(container: ContestExtracurricularFilterCafeteriaContainer, day: CalendarDay) = container.bind(day)
      }

      viewModel.isCalendarInit.observe(viewLifecycleOwner, EventObserver {
         if (it) binding.cafeteriaCalendar.notifyCalendarChanged()
      })
   }

   private fun initStartView() {
      viewModel.interestArray = arguments?.getStringArrayList(MY_INTEREST_LIST) as ArrayList<String>
      if (viewModel.interestArray.isNotEmpty()) {
         viewModel.interestArray.forEach {
            val btnInterest = when (it) {
               ChannelInterest.Planning.interest -> binding.planning
               ChannelInterest.Design.interest -> binding.design
               ChannelInterest.Programming.interest -> binding.programming
               ChannelInterest.IT.interest -> binding.it
               ChannelInterest.Data.interest -> binding.data
               ChannelInterest.Game.interest -> binding.game
               ChannelInterest.Marketing.interest -> binding.marketing
               ChannelInterest.Business.interest -> binding.business
               ChannelInterest.Economics.interest -> binding.economics
               ChannelInterest.Engineering.interest -> binding.engineering
               ChannelInterest.Art.interest -> binding.art
               ChannelInterest.Novel.interest -> binding.novel
               ChannelInterest.Lifestyle.interest -> binding.lifestyle
               ChannelInterest.Picture.interest -> binding.picture
               ChannelInterest.Culture.interest -> binding.culture
               ChannelInterest.Travel.interest -> binding.travel
               ChannelInterest.Environment.interest -> binding.environment
               ChannelInterest.Language.interest -> binding.language
               ChannelInterest.MediaContents.interest -> binding.mediaContents
               ChannelInterest.Paper.interest -> binding.paper
               ChannelInterest.Sports.interest -> binding.sports
               ChannelInterest.Dance.interest -> binding.dance
               ChannelInterest.Service.interest -> binding.service
               else -> null
            }
            btnInterest?.isSelected = true
            btnInterest?.setTypeface(null, Typeface.BOLD)

            if (btnInterest != null) {
               viewModel.choiceInterest.add(btnInterest)
               viewModel.interestTotal.value += 1
            }
         }
      }
   }
}