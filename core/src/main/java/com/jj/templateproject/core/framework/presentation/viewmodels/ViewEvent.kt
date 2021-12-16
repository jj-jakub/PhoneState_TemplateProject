package com.jj.templateproject.core.framework.presentation.viewmodels

data class ViewEvent<T>(val eventData: T) {

    private var hasBeenHandled = false

    fun handle(handler: (T) -> Unit) {
        if (hasBeenHandled.not()) {
            hasBeenHandled = true
            handler(eventData)
        }
    }
}

fun <T> T.event() = ViewEvent(this)