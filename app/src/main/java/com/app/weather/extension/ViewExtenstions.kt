package com.app.weather.extension

import android.os.Build
import android.text.Html
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import com.app.weather.R
import com.bumptech.glide.Glide

inline fun <T : View> T.onClick(crossinline func: T.() -> Unit) {
    setOnClickListener { func() }
}


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun ImageView.loadImg(url: String?, placeHolderId: Int = R.drawable.ic_launcher_background) {
    if (url.isNullOrEmpty()) {
        this.setImageResource(placeHolderId)
        return
    }
    Glide.with(context)
        .load(url)
        .into(this)
}

fun AppCompatTextView.asHtmlContent(html: String) {
    this.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(html)
    }
}