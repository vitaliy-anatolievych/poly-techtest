package com.mylibrary.core.viewmodels

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.mylibrary.core.type.Failure
import com.mylibrary.core.type.HandleOnce

fun <T : Any, L : LiveData<T>> LifecycleOwner.onSuccess(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))


fun <T : Any, L : LiveData<HandleOnce<T>>> LifecycleOwner.onSuccessOnce(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this) {
        it.getContentIfNotHandled()?.let(body)
    }

fun <L : LiveData<HandleOnce<Failure>>> LifecycleOwner.onFailure(liveData: L, body: (Failure?) -> Unit) =
    liveData.observe(this) {
        it.getContentIfNotHandled()?.let(body)
    }