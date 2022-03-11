package com.example.heysrealprojcet.api

import com.example.heysrealprojcet.model.LoginResponse
import retrofit2.http.GET

interface SettingApi {
   @GET("setup/categories")
   suspend fun getCategory(): retrofit2.Response<LoginResponse>
}