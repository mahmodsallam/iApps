@file:Suppress("DEPRECATION")

package com.task.iApps.util.binding_adapter

import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

object ViewBindingAdapters {
    @JvmStatic
    @BindingAdapter("textHtml")
    fun setText(view: TextView, textHtml: String) {
        view.text = Html.fromHtml(textHtml).toString()
    }

    @JvmStatic
    @BindingAdapter("visible")
    fun setVisibleGone(view: View, isVisible: Boolean?) {
        view.visibility = if (isVisible != false) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}