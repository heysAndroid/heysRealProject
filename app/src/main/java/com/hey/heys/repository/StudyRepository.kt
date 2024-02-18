package com.hey.heys.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hey.heys.api.StudyApi
import com.hey.heys.model.network.ChannelList
import com.hey.heys.model.network.NetworkResult
import com.hey.heys.model.network.Study
import com.hey.heys.model.network.StudyPagingSource
import com.hey.heys.model.network.response.CreateStudyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StudyRepository @Inject constructor(
   private val studyApi: StudyApi
) : BaseApiResponse() {
   suspend fun createStudy(token: String, study: Study): Flow<NetworkResult<CreateStudyResponse>> {
      return flow {
         emit(safeApiCall { studyApi.createStudy(token, study) })
      }.flowOn(Dispatchers.IO)
   }

   fun getStudyList(
      token: String,
      interest: ArrayList<String>?,
      lastRecruitDate: String?,
      purposes: ArrayList<String>?,
      online: String?,
      location: String?,
      includeClosed: Boolean?
   ): Flow<PagingData<ChannelList>> {
      return Pager(
         config = PagingConfig(pageSize = 30),
         pagingSourceFactory = { StudyPagingSource(studyApi, token, interest, lastRecruitDate, purposes, online, location, includeClosed) }
      ).flow
   }
}