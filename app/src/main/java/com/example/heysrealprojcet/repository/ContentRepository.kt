package com.example.heysrealprojcet.repository

import com.example.heysrealprojcet.api.ContentApi
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.ContentDetailResponse
import com.example.heysrealprojcet.model.network.response.ContentListResponse
import com.example.heysrealprojcet.model.network.response.SimpleResponse
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
      order:String?,
      interest: ArrayList<String>?,
      lastRecruitDate: String?,
      includeClosed: Boolean?,
      page: Int?,
      limit: Int?
   ): Flow<NetworkResult<ContentListResponse>> {
      return flow {
         emit(safeApiCall {
            contentApi.getContentList(
               token, type, order, interest, lastRecruitDate, includeClosed, page, limit)
         })
      }.flowOn(Dispatchers.IO)
   }

   fun getContentDetail(token: String, id: Int): Flow<NetworkResult<ContentDetailResponse>> {
      return flow {
         emit(safeApiCall { contentApi.getContentDetail(token, id) })
      }.flowOn(Dispatchers.IO)
   }

   fun contentViewCountUp(token: String, id: Int): Flow<NetworkResult<SimpleResponse>> {
      return flow {
         emit(safeApiCall { contentApi.contentViewCountUp(token, id) })
      }.flowOn(Dispatchers.IO)
   }

   fun contentAddBookmark(token: String, id: Int): Flow<NetworkResult<SimpleResponse>> {
      return flow {
         emit(safeApiCall { contentApi.contentAddBookmark(token, id) })
      }.flowOn(Dispatchers.IO)
   }

   fun contentRemoveBookmark(token: String, id: Int): Flow<NetworkResult<SimpleResponse>> {
      return flow {
         emit(safeApiCall { contentApi.contentRemoveBookmark(token, id) })
      }.flowOn(Dispatchers.IO)
   }
}