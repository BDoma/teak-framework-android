package com.tea.kotlin.android.lifecycles

import com.tea.kotlin.android.actions.Action

class SimpleLifecycle<Msg> : ActionLifecycle<Msg>() {
    private val actionAdapter = Action(this)
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

    fun dispatch(message: Msg) {
        actionAdapter.dispatch(message)
    }
}