package com.example.heysrealprojcet.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.heysrealprojcet.R
import com.example.heysrealprojcet.util.UserPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      setContentView(R.layout.main_activity)
      setNavigationGraph()
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
   }
}