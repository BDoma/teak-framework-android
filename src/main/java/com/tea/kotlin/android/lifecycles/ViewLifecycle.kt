package com.tea.kotlin.android.lifecycles

class ViewLifecycle {
    private val viewRelatedFunctions = arrayListOf<() -> Unit>()
    private var isViewCreated = false

    internal fun onViewCreated() {
        viewRelatedFunctions.forEach { it() }
        isViewCreated = true
    }

    fun runWhenViewCreated(function: () -> Unit) {
        if (isViewCreated) function()
        else viewRelatedFunctions.add(function)
    }
}