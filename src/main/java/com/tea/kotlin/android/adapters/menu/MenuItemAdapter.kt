package com.tea.kotlin.android.adapters.menu

import android.view.MenuItem
import com.tea.kotlin.android.adapters.BaseAdapter
import com.tea.kotlin.android.lifecycles.Lifecycle

class MenuItemAdapter(
    lifecycleOwner: Lifecycle<out Any>,
    resId: Int,
    private var onItemClickListener: android.view.View.OnClickListener? = null
) : BaseAdapter<MenuItem>() {
    init {
        lifecycleOwner.registerMenuItemAdapter(resId, this)
    }

    internal fun onClick(){
        onItemClickListener?.onClick(null)
    }

    fun setOnClickListener(listener: android.view.View.OnClickListener){
        onItemClickListener = listener
    }
}