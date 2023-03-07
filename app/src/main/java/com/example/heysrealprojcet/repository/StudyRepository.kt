package com.example.heysrealprojcet.repository

import com.example.heysrealprojcet.api.StudyApi
import com.example.heysrealprojcet.model.Study
import com.example.heysrealprojcet.model.network.NetworkResult
import com.example.heysrealprojcet.model.network.response.CreateStudyResponse
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
}