package com.example.heysrealprojcet.api

import com.example.heysrealprojcet.model.network.response.PingResponse
import retrofit2.Response
import retrofit2.http.GET

interface CommonApi {
   @GET("common/ping")
   suspend fun ping(): Response<PingResponse>
}