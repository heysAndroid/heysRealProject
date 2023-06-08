package com.example.heysrealprojcet

import android.app.Application
import com.chibatching.kotpref.Kotpref
import com.chibatching.kotpref.gsonpref.gson
import com.google.gson.Gson
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
      Kotpref.gson = Gson()
      application = this
   }
}