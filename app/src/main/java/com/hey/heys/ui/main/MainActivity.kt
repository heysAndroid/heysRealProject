package com.hey.heys.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsControllerCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.hey.heys.R
import com.hey.heys.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
   private lateinit var binding: MainActivityBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
//      binding = MainActivityBinding.inflate(layoutInflater)
//      setContentView(binding.root)
      binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
      // 상태바 및 아이콘 색상 변경
      window.apply {
         //상태바
         statusBarColor = Color.WHITE
         //상태바 아이콘(true: 검정 / false: 흰색)
         WindowInsetsControllerCompat(this, this.decorView).isAppearanceLightStatusBars = true
      }

      val intentText = intent.getStringExtra(Intent.EXTRA_TEXT)
      intentText?.let { setNavigationGraph(it) }
   }

   fun hideBottomNavigation(state: Boolean) {
      if(::binding.isInitialized){
         if(state) binding.bottomNavigation.visibility = View.GONE
         else binding.bottomNavigation.visibility = View.VISIBLE
      }
   }

   private fun setNavigationGraph(intent: String) {
      val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostMain) as NavHostFragment
      val navController = navHostFragment.navController
      val navGraph = navController.navInflater.inflate(R.navigation.sign_up_navigation)

      when (intent) {
         "login" -> {
            navGraph.setStartDestination(R.id.signInPhoneFragment)
         }

         "signUp" -> {
            navGraph.setStartDestination(R.id.signUpPhoneFragment)
         }

         else -> {
            navGraph.setStartDestination(R.id.main_navigation)
         }
      }
      navHostFragment.navController.graph = navGraph
      initNavigation()
   }

   private fun initNavigation() {
      NavigationUI.setupWithNavController(binding.bottomNavigation, findNavController(R.id.navHostMain))
      // 아이콘이 태마색으로 변경되는 것 막음
      binding.bottomNavigation.itemIconTintList = null
   }

   fun goToMyPage() {
      binding.bottomNavigation.selectedItemId = R.id.myPageFragment
   }

   fun goToMyChannel() {
      binding.bottomNavigation.selectedItemId = R.id.myChannelFragment
   }
}