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
   private lateinit var viewmodel: ContestExtracurricularFilterViewModel

   private lateinit var myInterestList: ArrayList<String>

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

      initStartView()

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

   private fun initStartView() {
      myInterestList = arguments?.getStringArrayList(MY_INTEREST_LIST) as ArrayList<String>

      for (i in myInterestList.indices) {
         val btnInterest = when (myInterestList[i]) {
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
      }
   }
}