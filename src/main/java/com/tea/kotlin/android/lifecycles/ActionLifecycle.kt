package com.tea.kotlin.android.lifecycles

import com.tea.kotlin.android.actions.Action

open class ActionLifecycle<Msg> {
    private var dispatch: ((Msg) -> Unit)? = null
    private val actions = arrayListOf<Action<out Msg>>()

    fun addDispatch(dispatch: (Msg) -> Unit) {
        this.dispatch = dispatch
        for (action in actions) {
            action.addDispatcher(dispatcher = dispatch)
        }
    }

    fun <T : Msg> registerAction(action: Action<T>) {
        actions.add(action)
        dispatch?.let { action.addDispatcher(it) }
    }

}