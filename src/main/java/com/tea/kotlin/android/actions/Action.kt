package com.tea.kotlin.android.actions

import com.tea.kotlin.android.common.ActionLifecycle

abstract class Action<Msg>(
    lifecycle: ActionLifecycle<Msg>,
    private val event: (() -> Unit)? = null) {
    private val dispatchers = arrayListOf<(Msg) -> Unit>()

    init {
        lifecycle.registerAction(this)
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