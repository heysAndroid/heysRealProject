package com.example.heysrealprojcet.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.databinding.MainActivityBinding
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
   private lateinit var binding: MainActivityBinding

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = MainActivityBinding.inflate(layoutInflater)
      setContentView(binding.root)
      setNavigationGraph()
   }

   fun hideBottomNavigation(state: Boolean) {
      if (state) binding.bottomNavigation.visibility = View.GONE
      else binding.bottomNavigation.visibility = View.VISIBLE
   }

   private fun setNavigationGraph() {
      val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostMain) as NavHostFragment
      val navController = navHostFragment.navController
      val navGraph = navController.navInflater.inflate(R.navigation.main_navigation)
      if (UserPreference.isExistingUser) {
         navGraph.startDestination = R.id.signUpPhoneFragment
      } else {
         navGraph.startDestination = R.id.mainFragment
      }
      navHostFragment.navController.graph = navGraph
      initNavigation()
   }

   private fun initNavigation() {
      NavigationUI.setupWithNavController(binding.bottomNavigation, findNavController(R.id.navHostMain))
      // 아이콘이 태마색으로 변경되는 것 막음
      binding.bottomNavigation.itemIconTintList = null
   }
}