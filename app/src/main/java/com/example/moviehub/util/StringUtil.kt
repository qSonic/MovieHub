package com.example.moviehub.util

object StringUtil {
    fun formatList(string: String): String {
        return string.replace("[", "").replace("]", "")
    }
}
