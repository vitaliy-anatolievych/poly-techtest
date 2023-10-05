package com.mylibrary.core.type

sealed class Failure {
    object UnknownError: Failure()
    object NetworkConnectionError: Failure()
    object ServerError: Failure()
}
