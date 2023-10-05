package com.mylibrary.core.network

import com.mylibrary.core.type.Either
import com.mylibrary.core.type.Failure
import retrofit2.Call
import retrofit2.Response

class Request constructor(private val networkHandler: NetworkHandler) {

    fun <T, R> make(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
        return when (networkHandler.isConnected) {
            true -> execute(call, transform)
            false -> Either.Left(Failure.NetworkConnectionError)
        }
    }

    private fun <T, R> execute(call: Call<T>, transform: (T) -> R): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSucceed()) {
                true -> Either.Right(transform((response.body()!!)))
                false -> Either.Left(Failure.ServerError) // if needed use parseError()
            }
        } catch (exception: Throwable) {
            exception.printStackTrace()
            Either.Left(Failure.UnknownError)
        }
    }
}

private fun <T> Response<T>.isSucceed(): Boolean {
    return isSuccessful && body() != null && code() in 200..299
}

// for parse uniq type error Need ErrorResponse
//private fun <T> Response<T>.parseError(): Failure {
//    return try {
//        val gson = Gson()
//        val jsonObject = JSONObject(errorBody()!!.string())
//        val errorResponse = gson.fromJson(jsonObject.toString(), ErrorResponse::class.java)
//
//        Log.e("ERROR_MESSAGE", errorResponse.message)
//        when (errorResponse.message) {
//            // Validation
//            else -> Failure.ServerError
//        }
//    } catch (e: Exception) {
//        e.printStackTrace()
//        Failure.ServerError
//    }
//}