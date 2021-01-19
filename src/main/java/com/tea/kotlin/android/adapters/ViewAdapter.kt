package com.tea.kotlin.android.adapters

import android.view.View
import com.tea.kotlin.android.common.Lifecycle

class ViewAdapter<T : View>(
    container: Lifecycle<out Any>,
    resId: Int,
    init: ((T) -> Unit)? = null
)  : BaseAdapter<T>(init) {
    init {
        container.registerViewAdapter(resId, this)
    }
}