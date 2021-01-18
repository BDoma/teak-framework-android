package com.tea.kotlin.android.actions

abstract class Action<Msg> {
    protected val dispatchers = arrayListOf<(Msg) -> Unit>()

    fun addDispatcher(dispatcher: (Msg) -> Unit){
        if (!dispatchers.contains(dispatcher)) dispatchers.add(dispatcher)
    }
}