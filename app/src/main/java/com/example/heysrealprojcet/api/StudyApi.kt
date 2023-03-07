package com.example.heysrealprojcet.api

import com.example.heysrealprojcet.model.Study
import com.example.heysrealprojcet.model.network.response.CreateStudyResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface StudyApi {
   @POST("channel/study")
   suspend fun createStudy(@Header("Authorization") token: String, @Body study: Study): Response<CreateStudyResponse>
}