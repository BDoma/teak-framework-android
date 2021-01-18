package com.tea.kotlin.android.adapters

abstract class BaseAdapter<T>(
    init: ((T) -> Unit)? = null
) {
    protected var item: T? = null
    private val functions = arrayListOf<(T) -> Unit>()

    init {
        init?.let { call(it) }
    }

    fun onCreated(item: T) {
        this.item = item
        for (function in functions) {
            function(item)
        }
        functions.clear()
    }

    fun call(function: (T) -> Unit) {
        val v = item
        if (v == null) functions.add(function)
        else function(v)
    }
}