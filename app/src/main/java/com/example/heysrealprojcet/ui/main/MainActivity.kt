package com.example.heysrealprojcet.ui.main

import android.os.Bundle
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
   private val binding by lazy {
      MainActivityBinding.inflate(layoutInflater)
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      initBinding()
      setNavigationGraph()
   }

   private fun initBinding() {
      setContentView(binding.root)
   }

   private fun setNavigationGraph() {
      val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostMain) as NavHostFragment
      val navController = navHostFragment.navController
      val navGraph = navController.navInflater.inflate(R.navigation.main_navigation)
      if (UserPreference.isExistingUser) {
         navGraph.startDestination = R.id.joinPhoneFragment
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