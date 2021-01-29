package com.tea.kotlin.android.component

open class MsgSender<Msg> {
    private val dispatchers = arrayListOf<(Msg) -> Unit>()

    fun dispatch(message: Msg) {
        for (dispatch in dispatchers) {
            dispatch(message)
        }
    }

    internal fun addDispatcher(dispatcher: (Msg) -> Unit) {
        if (!dispatchers.contains(dispatcher)) dispatchers.add(dispatcher)
    }
}