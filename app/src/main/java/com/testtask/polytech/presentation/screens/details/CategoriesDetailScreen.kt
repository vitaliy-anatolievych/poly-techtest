package com.testtask.polytech.presentation.screens.details

import android.graphics.Color
import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mylibrary.core.type.Failure
import com.mylibrary.core.viewmodels.onFailure
import com.mylibrary.core.viewmodels.onSuccess
import com.testtask.polytech.R
import com.testtask.polytech.databinding.FragmentCategoriesDetailsBinding
import com.testtask.polytech.domain.models.BookModel
import com.testtask.polytech.domain.models.CategoryModel
import com.testtask.polytech.presentation.contracts.nav
import com.testtask.polytech.presentation.core.BaseFragment
import com.testtask.polytech.presentation.screens.details.adapters.CategoriesDetailsRVAdapter
import com.testtask.polytech.presentation.utils.GlidePreload
import com.testtask.polytech.presentation.utils.LoadMoreViewVertical
import org.koin.androidx.viewmodel.ext.android.viewModel

class CategoriesDetailScreen : BaseFragment(R.layout.fragment_categories_details) {

    private val binding: FragmentCategoriesDetailsBinding by viewBinding()
    private val viewModel by viewModel<CategoriesDetailViewModel>()
    private val adapter = CategoriesDetailsRVAdapter()
    private lateinit var categoryModel: CategoryModel

    override var loadingView: View
        get() = binding.loading
        set(value) {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryModel =
            arguments?.getSerializable(MODEL_KEY) as CategoryModel

        adapterSettings()
        getAllBooks()

        with(viewLifecycleOwner) {
            onSuccess(viewModel.getBooksData, ::handleBooksList)
            onFailure(viewModel.failureData, ::handleFailure)
        }

    }

    private fun getAllBooks() {
        viewModel.getListOfBooksFromCategory(categoryModel)
        setLoadingState(LoadingState.LOADING)
    }

    private fun adapterSettings() = with(binding) {
        rvBooks.adapter = adapter

        with(adapter) {
            setDiffCallback(CategoriesDetailsRVAdapter.DiffCallBack())
            loadMoreModule.loadMoreView = LoadMoreViewVertical()
            loadMoreModule.preLoadNumber = CategoriesDetailsRVAdapter.COUNT_OF_PRELOAD_ELEMENTS
            loadMoreModule.isAutoLoadMore = true
        }

        adapter.onClickListener { url ->
            nav()?.goToBookDetails(url)
        }
    }

    private fun handleBooksList(list: List<BookModel>?) {

        list?.let { listOfBooks ->
            GlidePreload.preloadImages(requireContext(), listOfBooks) { isLoaded ->
                if (isLoaded) {
                    binding.root.setBackgroundColor(Color.WHITE)
                    setLoadingState(LoadingState.COMPLETE)
                    adapter.setList(listOfBooks)
                    sayAdapterLoadDataSuccessful()
                }
            }
        }
    }

    /**
     * @param failure - includes errors that occur when working with the network
     */
    override fun handleFailure(failure: Failure?) {
        setLoadingState(LoadingState.COMPLETE)
        alertMessage(getString(R.string.ure_not_online))
        activity?.onBackPressed()
    }

    private fun sayAdapterLoadDataSuccessful() = with(adapter) {
        loadMoreModule.loadMoreComplete()
        loadMoreModule.isEnableLoadMore = true
    }

    private fun sayAdapterLoadDataFailure() = with(adapter) {
        loadMoreModule.loadMoreFail()
        setLoadingState(LoadingState.ERROR)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        GlidePreload.dispose()
    }

    companion object {
        const val MODEL_KEY = "category_model"

        @JvmStatic
        fun newInstance(categoryModel: CategoryModel) = CategoriesDetailScreen().apply {
            arguments = Bundle().apply {
                putSerializable(MODEL_KEY, categoryModel)
            }
        }
    }
}