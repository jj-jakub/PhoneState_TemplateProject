package com.jj.templateproject.notes.featurenote.domain.utils

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
