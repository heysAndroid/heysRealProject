package com.example.heysrealprojcet.api

import com.example.heysrealprojcet.model.network.response.ContentListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ContentApi {
   @GET("content/extra")
   suspend fun getContentList(
      @Header("Authorization") token: String,
      @Query("type") type: String,
      @Query("interests") interests: ArrayList<String>?,
      @Query("lastRecruitDate") lastRecruitDate: String?,
      @Query("includeClosed") includeClosed: Boolean?,
      @Query("page") page: Int?,
      @Query("limit") limit: Int?,
   ): Response<ContentListResponse>
}