package com.jj.templateproject.core.domain.result

sealed class DataResult<out T> {

    private var hasBeenHandled = false

    class Success<out T>(private val data: T?) : DataResult<T>() {
        val dataValue: T?
            get() = data
    }

    class Error(val exception: Exception) : DataResult<Nothing>()

    fun getValue(): T? = if (this is Success) dataValue else null

    fun forceGetValue(): T = when (this) {
        is Success -> dataValue!!
        is Error -> throw exception
    }

    fun onSuccess(block: Success<T>.() -> Unit): DataResult<T> {
        if (this is Success && hasBeenHandled.not()) {
            block()
            hasBeenHandled = true
        }
        return this
    }

    fun onError(block: Error.() -> Unit): DataResult<T> {
        if (this is Error && hasBeenHandled.not()) {
            block()
            hasBeenHandled = true
        }
        return this
    }
}

suspend fun <T> getDataResult(block: suspend () -> T?): DataResult<T> {
    return try {
        DataResult.Success(block.invoke())
    } catch (e: Exception) {
        e.printStackTrace()
        DataResult.Error(e)
    }
}