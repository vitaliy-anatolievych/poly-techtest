package com.testtask.polytech.presentation.utils

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.testtask.polytech.domain.models.BookModel

object GlidePreload {

    @Volatile
    private var downloadedImages = 0

    @Volatile
    private var isActiveSubscribe = false

    fun preloadImages(context: Context, list: List<BookModel>, isLoaded: (Boolean) -> Unit) {
        isActiveSubscribe = true
        downloadedImages = 0

        list.forEach { image ->
            Thread {
                Glide.with(context)
                    .asGif()
                    .load(image.imageUrl)
                    .listener(object : RequestListener<GifDrawable?> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<GifDrawable?>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            downloadedImages++
                            if (downloadedImages >= list.size && isActiveSubscribe) isLoaded.invoke(true)
                            return false
                        }

                        override fun onResourceReady(
                            resource: GifDrawable?,
                            model: Any?,
                            target: Target<GifDrawable?>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            downloadedImages++

                            if (downloadedImages >= list.size && isActiveSubscribe) isLoaded.invoke(true)
                            return false
                        }
                    }).preload()
            }.start()
        }
    }

    fun dispose() {
        isActiveSubscribe = false
    }
}