package com.testtask.polytech.presentation.core

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.mylibrary.core.type.Failure

abstract class BaseFragment(@LayoutRes res: Int) : Fragment(res) {

    abstract var loadingView: View
    open var errorView: View? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setOnClickListener { } // empty click
    }

    fun setLoadingState(state: LoadingState) {

        when (state) {
            LoadingState.LOADING -> {
                loadingView.visibility = View.VISIBLE
                errorView?.visibility = View.INVISIBLE
            }
            LoadingState.COMPLETE -> loadingView.visibility = View.INVISIBLE
            LoadingState.ERROR -> {
                loadingView.visibility = View.INVISIBLE
                errorView?.visibility = View.VISIBLE
            }
        }
    }

    open fun handleFailure(failure: Failure?) {
        setLoadingState(LoadingState.ERROR)
    }

    fun alertMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    enum class LoadingState {
        LOADING, COMPLETE, ERROR
    }
}