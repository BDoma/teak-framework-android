package com.tea.kotlin.android.collections

interface Comparator<T> {
    fun itemsAreSame(first: T, second: T): Boolean
    fun contentIsSame(first: T, second: T): Boolean
}