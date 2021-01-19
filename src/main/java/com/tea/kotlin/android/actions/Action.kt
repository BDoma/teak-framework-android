package com.tea.kotlin.android.actions

import com.tea.kotlin.android.Activity

abstract class Action<Msg>(
    activity: Activity<out Any, Msg>,
    private val event: (() -> Unit)? = null) {
    private val dispatchers = arrayListOf<(Msg) -> Unit>()

    init {
        activity.registerAction(this)
    }

    protected fun dispatch(message: Msg) {
        event?.invoke()
        for (dispatch in dispatchers) {
            dispatch(message)
        }
    }

    internal fun addDispatcher(dispatcher: (Msg) -> Unit) {
        if (!dispatchers.contains(dispatcher)) dispatchers.add(dispatcher)
    }
}