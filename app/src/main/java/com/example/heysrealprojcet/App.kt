package com.example.heysrealprojcet

import android.app.Application
import com.chibatching.kotpref.Kotpref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
   companion object {
      private lateinit var application: App
      fun getInstance(): App = application
   }

   override fun onCreate() {
      super.onCreate()
      Kotpref.init(applicationContext)
      application = this
   }
}