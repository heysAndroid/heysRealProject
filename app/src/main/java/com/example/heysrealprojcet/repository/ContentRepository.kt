package com.example.heysrealprojcet.repository

import com.example.heysrealprojcet.api.ContentApi
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.ContentListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ContentRepository @Inject constructor(
   private val contentApi: ContentApi
) : BaseApiResponse() {
   fun getContentList(
      token: String,
      type: String,
      interest: ArrayList<String>?,
      lastRecruitDate: String?,
      includeClosed: Boolean?,
      page: Int?,
      limit: Int?
   ): Flow<NetworkResult<ContentListResponse>> {
      return flow {
         emit(safeApiCall {
            contentApi.getContentList(
               token, type, interest, lastRecruitDate, includeClosed, page, limit)
         })
      }.flowOn(Dispatchers.IO)
   }
}