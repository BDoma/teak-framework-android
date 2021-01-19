package com.tea.kotlin.android.actions

import android.view.View
import com.tea.kotlin.android.Activity

class ClickListener<Msg>(
    activity: Activity<out Any, Msg>,
    private val msg: Msg,
    function: (() -> Unit)? = null
) : Action<Msg>(activity, function), View.OnClickListener {

    override fun onClick(p0: View?) {
        dispatch(msg)
    }

}