package com.example.heys.api

import com.example.heys.model.network.Study
import com.example.heys.model.network.response.ChannelListResponse
import com.example.heys.model.network.response.CreateStudyResponse
import retrofit2.Response
import retrofit2.http.*

interface StudyApi {
   @POST("channel/study")
   suspend fun createStudy(@Header("Authorization") token: String, @Body study: Study): Response<CreateStudyResponse>

   @GET("channel/study")
   suspend fun getStudyList(
      @Header("Authorization") token: String,
      @Query("interests") interests: ArrayList<String>?,
      @Query("lastRecruitDate", encoded = true) lastRecruitDate: String?,
      @Query("purposes") purposes: ArrayList<String>?,
      @Query("online") online: String?,
      @Query("location", encoded = true) location: String?,
      @Query("includeClosed") includeClosed: Boolean?,
      @Query("page") page: Int?,
      @Query("limit") limit: Int?,
   ): Response<ChannelListResponse>
}