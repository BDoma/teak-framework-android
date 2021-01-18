package com.tea.kotlin.android.actions

import android.view.View

class ClickListener<Msg>(
    private val msg: Msg,
    private val function : (() -> Unit)? = null
) : Action<Msg>(), View.OnClickListener {

    override fun onClick(p0: View?) {
        function?.invoke()
        for (dispatch in dispatchers) {
            dispatch(msg)
        }
    }

}