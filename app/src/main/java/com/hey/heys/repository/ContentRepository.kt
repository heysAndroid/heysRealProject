package com.hey.heys.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hey.heys.api.ContentApi
import com.hey.heys.model.network.Content
import com.hey.heys.model.network.ContentPagingSource
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.response.ContentDetailResponse
import com.hey.heys.model.network.response.SimpleResponse
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
      type: String?,
      order: String?,
      interest: ArrayList<String>?,
      lastRecruitDate: String?,
      includeClosed: Boolean?
   ): Flow<PagingData<Content>> {
      return Pager(
         config = PagingConfig(pageSize = 30),
         pagingSourceFactory = { ContentPagingSource(contentApi, token, type, order, interest, lastRecruitDate, includeClosed) }
      ).flow
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