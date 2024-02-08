package com.hey.heys.api

import com.hey.heys.model.network.response.ContentDetailResponse
import com.hey.heys.model.network.response.ContentListResponse
import com.hey.heys.model.network.response.SimpleResponse
import retrofit2.Response
import retrofit2.http.*

interface ContentApi {
   @GET("content/extra")
   suspend fun getContentList(
      @Header("Authorization") token: String,
      @Query("type") type: String?,
      @Query("order") order: String?,
      @Query("interests", encoded = true) interests: ArrayList<String>?,
      @Query("lastRecruitDate", encoded = true) lastRecruitDate: String?,
      @Query("includeClosed") includeClosed: Boolean?,
      @Query("page") page: Int?,
      @Query("limit") limit: Int?,
   ): Response<ContentListResponse>

   @GET("content/extra/{id}")
   suspend fun getContentDetail(@Header("Authorization") token: String, @Path(value = "id") id: Int): Response<ContentDetailResponse>

   @PUT("content/view-count-up/{contentId}")
   suspend fun contentViewCountUp(@Header("Authorization") token: String, @Path(value = "contentId") id: Int): Response<SimpleResponse>

   @PUT("content/add-bookmark/{contentId}")
   suspend fun contentAddBookmark(@Header("Authorization") token: String, @Path(value = "contentId") id: Int): Response<SimpleResponse>

   @PUT("content/remove-bookmark/{contentId}")
   suspend fun contentRemoveBookmark(@Header("Authorization") token: String, @Path(value = "contentId") id: Int): Response<SimpleResponse>
}