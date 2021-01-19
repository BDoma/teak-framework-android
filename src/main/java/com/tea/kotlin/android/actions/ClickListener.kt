package com.tea.kotlin.android.actions

import android.view.View

class ClickListener<Msg>(
    private val msg: Msg,
    function : (() -> Unit)? = null
) : Action<Msg>(function), View.OnClickListener {

    override fun onClick(p0: View?) {
        dispatch(msg)
    }

}