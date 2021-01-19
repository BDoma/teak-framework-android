package com.tea.kotlin.android.adapters

import android.view.MenuItem
import com.tea.kotlin.android.Activity

class OptionsMenuItemAdapter(
    activity: Activity<out Any, out Any>,
    resId: Int,
    private var onItemClickListener: android.view.View.OnClickListener? = null
) : BaseAdapter<MenuItem>() {
    init {
        activity.registerMenuItemAdapter(resId, this)
    }

    internal fun onClick(){
        onItemClickListener?.onClick(null)
    }

    fun setOnClickListener(listener: android.view.View.OnClickListener){
        onItemClickListener = listener
    }
}