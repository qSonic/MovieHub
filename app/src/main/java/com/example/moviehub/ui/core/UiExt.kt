package com.example.moviehub.ui.core

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun View?.bindVisibility(visible: Boolean) {
    if (this == null) {
        return
    }
    val visibility = if (visible) View.VISIBLE else View.GONE
    this.visibility = visibility
}

inline fun <T> Fragment.observeChanges(
    liveData: LiveData<T>?,
    crossinline changes: (t: T) -> Unit
) {
    if (liveData == null) {
        return
    }

    liveData.observe(
        viewLifecycleOwner,
        Observer {
            if (it != null) {
                changes(it)
            }
        }
    )
}
