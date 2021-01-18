package com.tea.kotlin.android.adapters

import android.view.View
import com.tea.kotlin.android.Activity

class ViewAdapter<T : View>(
    activity: Activity<out Any, out Any>,
    resId: Int,
    init: ((T) -> Unit)? = null
)  : BaseAdapter<T>(init) {
    init {
        activity.registerViewAdapter(resId, this)
    }
}