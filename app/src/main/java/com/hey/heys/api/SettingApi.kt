package com.hey.heys.api

import com.hey.heys.model.network.response.SignUpResponse
import retrofit2.Response
import retrofit2.http.GET

interface SettingApi {
   @GET("setup/categories")
   suspend fun getCategory(): Response<SignUpResponse>
}