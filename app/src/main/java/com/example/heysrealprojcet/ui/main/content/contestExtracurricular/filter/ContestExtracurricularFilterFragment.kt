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
import com.example.heysrealprojcet.ui.main.MainFragment.Companion.MY_INTEREST_LIST
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

class ContestExtracurricularFilterFragment : Fragment() {
   private lateinit var binding: ContestExtracurricularFilterFragmentBinding
   private lateinit var viewModel: ContestExtracurricularFilterViewModel

   private var currentTime = YearMonth.now()
   private val calendarFormatter = DateTimeFormatter.ofPattern("yyyy년 M월")
   private val selectedFormatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일")

   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = ContestExtracurricularFilterFragmentBinding.inflate(inflater, container, false)
      viewModel = ContestExtracurricularFilterViewModel(binding.cafeteriaCalendar)
      binding.vm = viewModel
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this

      initStartView()

      binding.btnApply.setOnClickListener { findNavController().navigateUp() }

      viewModel.selectedDate
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
   }

   private fun initStartView() {
      viewModel.interestArray = arguments?.getStringArrayList(MY_INTEREST_LIST) as ArrayList<String>

      for (i in viewModel.interestArray.indices) {
         val btnInterest = when (viewModel.interestArray[i]) {
            "기획/아이디어" -> binding.planning
            "디자인" -> binding.design
            "개발" -> binding.develop
            "IT/SW" -> binding.sw
            "데이터/인공지능" -> binding.data
            "게임" -> binding.game
            "광고/마케팅" -> binding.marketing
            "경영/비즈니스" -> binding.business
            "금융/경제" -> binding.economics
            "과학/공학" -> binding.science
            "미술/건축" -> binding.art
            "인문학/소설/웹툰" -> binding.novel
            "패션/라이프스타일" -> binding.lifestyle
            "사진/영상/UCC" -> binding.photo
            "문화/교육" -> binding.culture
            "해외/관광" -> binding.tour
            "환경/식품" -> binding.food
            "외국/언어" -> binding.language
            "미디어 콘텐츠/전시" -> binding.media
            "학술/논문" -> binding.scholarship
            "스포츠/음악" -> binding.sports
            "댄스/무용" -> binding.dance
            "공공/봉사" -> binding.service
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