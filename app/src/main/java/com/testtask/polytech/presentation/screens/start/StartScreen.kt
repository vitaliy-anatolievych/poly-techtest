package com.testtask.polytech.presentation.screens.start

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.children
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mylibrary.core.type.Failure
import com.mylibrary.core.viewmodels.onFailure
import com.mylibrary.core.viewmodels.onSuccess
import com.testtask.polytech.R
import com.testtask.polytech.databinding.FragmentCategoriesBinding
import com.testtask.polytech.domain.models.CategoryModel
import com.testtask.polytech.presentation.contracts.nav
import com.testtask.polytech.presentation.core.BaseFragment
import com.testtask.polytech.presentation.screens.start.adapters.StartRVAdapter
import com.testtask.polytech.presentation.utils.MainViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class StartScreen : BaseFragment(R.layout.fragment_categories) {

    private val binding: FragmentCategoriesBinding by viewBinding()
    private val viewModel by activityViewModel<MainViewModel>()
    private val adapter = StartRVAdapter()

    override var loadingView: View
        get() = binding.loading
        set(_) {}

     override var errorView: View?
        get() = binding.llError
        set(_) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterSettings()
        getAllCategories()

        with(viewLifecycleOwner) {
            onSuccess(viewModel.getCategoriesData, ::handleCategoriesList)
            onFailure(viewModel.failureData, ::handleFailure)
        }
    }

    private fun adapterSettings() = with(binding) {
        rvCategories.adapter = adapter

        adapter.onClickListener { category ->
            nav()?.goToCategoryDetails(category)
        }
    }

    private fun getAllCategories() {
        if (viewModel.getCategoriesData.value == null) {
            viewModel.getCategoriesList()
        }
        setLoadingState(LoadingState.LOADING)
    }

    private fun handleCategoriesList(list: List<CategoryModel>?) {
        setLoadingState(LoadingState.COMPLETE)

        list?.let { categoryList ->
            adapter.submitList(categoryList)
        }
    }

    /**
     * @param failure - includes errors that occur when working with the network
     */
    override fun handleFailure(failure: Failure?) {
        setLoadingState(LoadingState.ERROR)
        alertMessage(getString(R.string.ure_not_online))

        binding.btnError.setOnClickListener {
            getAllCategories()
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = StartScreen()
    }
}