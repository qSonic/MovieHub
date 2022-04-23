package com.example.moviehub.util

sealed class Resource<out T>(val data: T?, val message: String?) {

    class Success<out T>(data: T) : Resource<T>(data = data, message = null)
    class Error<out T>(message: String?) : Resource<T>(data = null, message = message)
    class Loading<out T>() : Resource<T>(data = null, message = null)
}
