package com.task.iApps.util.binding_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load

object ImageViewBindingAdapters {
    @JvmStatic
    @BindingAdapter("url")
    fun setUrl(view: ImageView, url: String?) {
        view.load(url) {
            crossfade(true)
        }
    }
}