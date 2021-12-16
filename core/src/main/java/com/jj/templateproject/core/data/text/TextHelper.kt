package com.jj.templateproject.core.data.text

import android.text.Html

class TextHelper {
    fun htmlToString(htmlString: String?): String? {
        val pureString = when (htmlString) {
            null -> null
            else -> Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY).toString()
        }
        return pureString?.trim()
    }
}