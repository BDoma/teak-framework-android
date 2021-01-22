package com.tea.kotlin.android.lifecycles

import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.tea.kotlin.android.adapters.ViewAdapter
import com.tea.kotlin.android.adapters.menu.MenuItemAdapter

class Lifecycle<Msg> : ActionLifecycle<Msg>() {
    private val viewAdapters = arrayListOf<Pair<Int, ViewAdapter<out View>>>()
    private var rootView: View? = null

    private val menuItemAdapters = arrayListOf<Pair<Int, MenuItemAdapter>>()
    private var optionsMenu: Menu? = null

    fun onViewCreated(view: View) {
        for (adapter in viewAdapters) {
            adapter.second.onCreated(view.findViewById(adapter.first))
        }
        rootView = view
    }

    internal fun onOptionsMenuCreated(menu: Menu) {
        optionsMenu = menu
        for (menuAdapter in menuItemAdapters) {
            notifyMenuItem(menu, menuAdapter)
        }
    }

    internal fun onOptionsItemSelected(item: MenuItem) : Boolean {
        val selected = menuItemAdapters.find { it.first == item.itemId }
        selected?.let {
            selected.second.onClick()
            return true
        }
        return false
    }

    private fun notifyMenuItem(menu: Menu, menuItemAdapter: Pair<Int, MenuItemAdapter>) {
        menu.findItem(menuItemAdapter.first)?.let { menuItem -> menuItemAdapter.second.onCreated(menuItem) }
    }

    fun <T : View> registerViewAdapter(resId: Int, viewAdapter: ViewAdapter<T>) {
        viewAdapters.add(Pair(resId, viewAdapter))
        rootView?.let {
            viewAdapter.onCreated(it.findViewById(resId))
        }
    }

    fun registerMenuItemAdapter(resId: Int, adapter: MenuItemAdapter) {
        menuItemAdapters.add(Pair(resId, adapter))
        optionsMenu?.let { notifyMenuItem(it, Pair(resId, adapter)) }
    }
}