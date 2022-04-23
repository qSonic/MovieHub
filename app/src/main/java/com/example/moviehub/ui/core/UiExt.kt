package com.example.moviehub.ui.core

import android.view.View

fun View?.bindVisibility(visible: Boolean) {
    if (this == null) {
        return
    }
    val visibility = if (visible) View.VISIBLE else View.GONE
    this.visibility = visibility
}

