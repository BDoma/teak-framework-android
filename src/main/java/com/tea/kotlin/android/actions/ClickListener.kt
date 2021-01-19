package com.tea.kotlin.android.actions

import android.view.View
import com.tea.kotlin.android.common.ActionLifecycle

class ClickListener<Msg>(
    lifecycle: ActionLifecycle<Msg>,
    private val msg: Msg,
    function: (() -> Unit)? = null
) : Action<Msg>(lifecycle, function), View.OnClickListener {

    override fun onClick(p0: View?) {
        dispatch(msg)
    }

}