package com.tea.kotlin.android.actions

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.tea.kotlin.android.lifecycles.ActionLifecycle

class MenuItemClickListener<Msg>(
    lifecycle: ActionLifecycle<Msg>,
    private val transform: ((Int) -> Msg?)
) : Action<Msg>(lifecycle), Toolbar.OnMenuItemClickListener {
    override fun onMenuItemClick(menuItem: MenuItem?): Boolean {
        menuItem?.let {
            transform(menuItem.itemId)?.let {
                dispatch(it)
                return true
            }
        }
        return false
    }


}