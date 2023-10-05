package com.testtask.polytech.presentation.screens.details.adapters

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.testtask.polytech.R
import com.testtask.polytech.databinding.ItemBookBinding
import com.testtask.polytech.domain.models.BookModel

typealias OnBookClickListener = (String) -> Unit
class CategoriesDetailsRVAdapter :
    BaseQuickAdapter<BookModel, CategoriesDetailsRVAdapter.CategoriesDetailsViewHolder>(R.layout.item_book),
    LoadMoreModule {

    private var onBookClickListener: OnBookClickListener? = null

    inner class CategoriesDetailsViewHolder(private val view: View) :
        BaseViewHolder(view) {
        private val binding = ItemBookBinding.bind(view)

        fun bind(model: BookModel) = with(binding) {

            Glide.with(view)
                .load(model.imageUrl)
                .into(imgBook)

            tvRank.text = model.rating.toString()
            title.text = model.displayName
            tvDescription.text = model.description
            tvAuthor.text = "${model.author} & ${model.distributive}"

            imgLinkBuy.setOnClickListener {
                onBookClickListener?.invoke(model.productUrl)
            }
        }
    }

    /**
     * @return URL on product in web
     */
    fun onClickListener(listener: OnBookClickListener) {
        onBookClickListener = listener
    }

    override fun convert(holder: CategoriesDetailsViewHolder, item: BookModel) {
        holder.bind(item)
    }

    class DiffCallBack() : DiffUtil.ItemCallback<BookModel>() {
        override fun areItemsTheSame(oldItem: BookModel, newItem: BookModel): Boolean {
            return oldItem.isbn10 == newItem.isbn10
        }

        override fun areContentsTheSame(
            oldItem: BookModel,
            newItem: BookModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        const val COUNT_OF_PRELOAD_ELEMENTS = 10
    }
}