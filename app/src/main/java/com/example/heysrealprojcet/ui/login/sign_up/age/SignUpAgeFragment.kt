package com.example.heysrealprojcet.ui.login.sign_up.age

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.SignUpAgeFragmentBinding
import com.example.heysrealprojcet.ui.main.MainActivity
import com.example.heysrealprojcet.util.UserPreference
import java.time.LocalDate
import java.util.Calendar

class SignUpAgeFragment : Fragment() {
   private lateinit var binding: SignUpAgeFragmentBinding
   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
      binding = SignUpAgeFragmentBinding.inflate(inflater, container, false)
      return binding.root
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onResume() {
      super.onResume()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(true)
   }

   override fun onDestroy() {
      super.onDestroy()
      val mainActivity = activity as MainActivity
      mainActivity.hideBottomNavigation(false)
   }

   @RequiresApi(Build.VERSION_CODES.O)
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding.lifecycleOwner = this
      binding.btnBack.setOnClickListener { findNavController().navigateUp() }
      binding.okButton.setOnClickListener { goToInterest() }
      binding.datePicker.maxDate = Calendar.getInstance().timeInMillis
      binding.datePicker.setOnDateChangedListener { _, year, month, dayOfMonth ->
         enableOkButton()
         UserPreference.birthday = "$year-${(month + 1).toString().convertSingleToDoubleDigit()}-${dayOfMonth.toString().convertSingleToDoubleDigit()}"
         val birthday = LocalDate.of(year, month + 1, dayOfMonth)
         UserPreference.age = getInternationalAge(birthday)
      }
   }

   private fun enableOkButton() {
      binding.okButton.isEnabled = true
   }

   private fun getInternationalAge(birthday: LocalDate): Int {
      val now = LocalDate.now()
      var internationalAge = now.minusYears(birthday.year.toLong()).year
      if (birthday.plusYears(internationalAge.toLong()).isAfter(now)) {
         internationalAge -= 1
      }

      return internationalAge
   }

   private fun goToInterest() {
      findNavController().navigate(R.id.action_signUpAgeFragment_to_signUpInterestFragment)
   }

   fun String.convertSingleToDoubleDigit(): String = if (this.length < 2) "0$this" else this
}