package com.example.moviehub.data.api

import android.util.Log
import com.example.moviehub.util.Resource
import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                Log.d("retrofit", body.toString())
                if (body != null) return Resource.Success(data = body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
    private fun <T> error(message: String): Resource<T> {
        Log.e("remoteDataSource", message)
        return Resource.Error("Network call has failed for a following reason: $message")
    }
}
