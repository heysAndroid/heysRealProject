package com.example.heys.model.network

/*
* API 응답 처리하기 위한 sealed class
* 뷰모델에서 API 응답 처리를 간편하게 하도록 정리해놓음.
 */

sealed class NetworkResult<T>(
   val data: T? = null,
   val message: String? = null
) {
   class Success<T>(data: T) : NetworkResult<T>(data)
   class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
   class Loading<T> : NetworkResult<T>()
}