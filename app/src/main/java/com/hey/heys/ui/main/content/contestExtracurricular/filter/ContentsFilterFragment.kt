package com.hey.heys.ui.main.content.contestExtracurricular.filter

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hey.heys.R
import com.hey.heys.databinding.ContentsFilterFragmentBinding
import com.hey.heys.enums.ChannelInterest
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale

class ContentsFilterFragment : Fragment() {
   private lateinit var binding: ContentsFilterFragmentBinding
   lateinit var viewModel: ContentsFilterViewModel

   private var currentTime = YearMonth.now()
   private val calendarFormatter = DateTimeFormatter.ofPattern("yyyy년 M월")
   private val selectedFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ContentsFilterFragmentBinding.inflate(inflater, container, false)
      viewModel = ViewModelProvider(requireActivity())[ContentsFilterViewModel::class.java]
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      initView()
      setCalendar()
      setCalendarBack()
      setCalendarForward()

      binding.btnClose.setOnClickListener { findNavController().navigateUp() }
      binding.btnInit.setOnClickListener {
         viewModel.init()
         unselectAllInterest()
      }
      binding.btnApply.setOnClickListener {
         val previousFragment = findNavController().previousBackStackEntry?.destination?.displayName
         if (previousFragment?.contains("contest") == true) {
            val action = ContentsFilterFragmentDirections.backToContestList("Default")
            findNavController().navigate(action)
         } else {
            val action = ContentsFilterFragmentDirections.backToExtracurricularList("Default")
            findNavController().navigate(action)
         }
      }

      binding.yearMonth.text = currentTime.format(calendarFormatter)
   }

   private fun setCalendar() {
      binding.cafeteriaCalendar.apply {
         stopScroll()
         isNestedScrollingEnabled = false
         itemAnimator = null

         setup(YearMonth.now(), YearMonth.now(), WeekFields.of(Locale.getDefault()).firstDayOfWeek)

         dayBinder = object : DayBinder<ContestExtracurricularFilterCafeteriaContainer> {
            override fun create(view: View): ContestExtracurricularFilterCafeteriaContainer =
               ContestExtracurricularFilterCafeteriaContainer(view, binding.cafeteriaCalendar, viewModel)

            override fun bind(container: ContestExtracurricularFilterCafeteriaContainer, day: CalendarDay) = container.bind(day)
         }
      }

      viewModel.isCalendarInit.observe(viewLifecycleOwner, com.hey.heys.EventObserver {
         if (it) binding.cafeteriaCalendar.notifyCalendarChanged()
      })

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
   }

   private fun setCalendarBack() {
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
   }

   private fun setCalendarForward() {
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
   }

   private fun initView() {
      viewModel.selectedInterest.observe(viewLifecycleOwner) { array ->
         unselectAllInterest()
         array.forEach {
            when (it) {
               ChannelInterest.Planning.interest -> selectButton(binding.planning)
               ChannelInterest.Design.interest -> selectButton(binding.design)
               ChannelInterest.Programming.interest -> selectButton(binding.programming)
               ChannelInterest.IT.interest -> selectButton(binding.it)
               ChannelInterest.Data.interest -> selectButton(binding.data)
               ChannelInterest.Game.interest -> selectButton(binding.game)
               ChannelInterest.Marketing.interest -> selectButton(binding.marketing)
               ChannelInterest.Business.interest -> selectButton(binding.business)
               ChannelInterest.Economics.interest -> selectButton(binding.economics)
               ChannelInterest.Engineering.interest -> selectButton(binding.engineering)
               ChannelInterest.Art.interest -> selectButton(binding.art)
               ChannelInterest.Novel.interest -> selectButton(binding.novel)
               ChannelInterest.Lifestyle.interest -> selectButton(binding.lifestyle)
               ChannelInterest.Picture.interest -> selectButton(binding.picture)
               ChannelInterest.Culture.interest -> selectButton(binding.culture)
               ChannelInterest.Travel.interest -> selectButton(binding.travel)
               ChannelInterest.Environment.interest -> selectButton(binding.environment)
               ChannelInterest.Language.interest -> selectButton(binding.language)
               ChannelInterest.MediaContents.interest -> selectButton(binding.mediaContents)
               ChannelInterest.Paper.interest -> selectButton(binding.paper)
               ChannelInterest.Sports.interest -> selectButton(binding.sports)
               ChannelInterest.Dance.interest -> selectButton(binding.dance)
               ChannelInterest.Service.interest -> selectButton(binding.service)
               else -> null
            }
         }
      }
   }

   private fun unselectButton(view: View) {
      val button = view as Button
      button.isSelected = false
      button.setTypeface(null, Typeface.NORMAL)
   }

   private fun selectButton(view: View) {
      val button = view as Button
      button.isSelected = true
      button.setTypeface(null, Typeface.BOLD)
   }

   private fun unselectAllInterest() {
      unselectButton(binding.planning)
      unselectButton(binding.design)
      unselectButton(binding.programming)
      unselectButton(binding.it)
      unselectButton(binding.data)
      unselectButton(binding.game)
      unselectButton(binding.marketing)
      unselectButton(binding.business)
      unselectButton(binding.economics)
      unselectButton(binding.engineering)
      unselectButton(binding.art)
      unselectButton(binding.novel)
      unselectButton(binding.lifestyle)
      unselectButton(binding.picture)
      unselectButton(binding.culture)
      unselectButton(binding.travel)
      unselectButton(binding.environment)
      unselectButton(binding.language)
      unselectButton(binding.mediaContents)
      unselectButton(binding.paper)
      unselectButton(binding.sports)
      unselectButton(binding.dance)
      unselectButton(binding.service)
   }
}