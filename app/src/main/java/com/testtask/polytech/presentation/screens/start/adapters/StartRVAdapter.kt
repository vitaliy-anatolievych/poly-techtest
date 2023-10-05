package com.testtask.polytech.presentation.screens.start.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.testtask.polytech.R
import com.testtask.polytech.databinding.ItemCategoryBinding
import com.testtask.polytech.domain.models.CategoryModel

typealias OnClickCategoryListener = (CategoryModel) -> Unit
class StartRVAdapter:
    ListAdapter<CategoryModel, StartRVAdapter.StartViewHolder>(DiffCallBack()) {

    private var onClickListener: OnClickCategoryListener? = null

    inner class StartViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemCategoryBinding.bind(view)

        fun bind(model: CategoryModel) = with(binding) {
            tvCategoryName.text = model.displayName
            tvDatePublication.text = model.newestPublishedDate

            cardCategory.setOnClickListener {
                onClickListener?.invoke(model)
            }
        }
    }

    fun onClickListener(listener: OnClickCategoryListener) {
        onClickListener = listener
    }

    class DiffCallBack() : DiffUtil.ItemCallback<CategoryModel>() {
        override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
            return oldItem.displayNameEncoded == newItem.displayNameEncoded
        }

        override fun areContentsTheSame(
            oldItem: CategoryModel,
            newItem: CategoryModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_category, parent, false)

        return StartViewHolder(view)
    }

    override fun onBindViewHolder(holder: StartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}