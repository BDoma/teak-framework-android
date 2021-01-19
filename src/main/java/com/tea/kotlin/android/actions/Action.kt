package com.tea.kotlin.android.actions

abstract class Action<Msg>(private val event: (() -> Unit)? = null) {
    private val dispatchers = arrayListOf<(Msg) -> Unit>()

    protected fun dispatch(message: Msg) {
        event?.invoke()
        for (dispatch in dispatchers) {
            dispatch(message)
        }
    }

    fun addDispatcher(dispatcher: (Msg) -> Unit) {
        if (!dispatchers.contains(dispatcher)) dispatchers.add(dispatcher)
    }
}