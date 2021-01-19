package com.tea.kotlin.android

import android.graphics.ColorSpace
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tea.kotlin.Runtime
import com.tea.kotlin.android.actions.Action
import com.tea.kotlin.android.adapters.OptionsMenuItemAdapter
import com.tea.kotlin.android.adapters.ViewAdapter


abstract class Activity<Model, Msg> : AppCompatActivity() {
    private lateinit var runtime: Runtime<Model, Msg>

    private val actions = arrayListOf<Action<out Msg>>()

    private val viewAdapters = arrayListOf<Pair<Int, ViewAdapter<out View>>>()
    private var viewCreated = false

    private val menuItemAdapters = arrayListOf<Pair<Int, OptionsMenuItemAdapter>>()
    private var optionsMenu: Menu? = null

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runtime = Runtime(::init, view = ::myView, ::update)
        setContentView(getLayout())
        for (adapter in viewAdapters) {
            adapter.second.onCreated(findViewById(adapter.first))
        }
        viewCreated = true
        onViewCreated()
    }

    private fun myView(model: Model, dispatch: (Msg) -> Unit){
        for (action in actions) {
            action.addDispatcher(dispatcher = dispatch)
        }
        view(model, dispatch)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getOptionsMenu()?.let {
            val inflater = menuInflater
            inflater.inflate(it, menu)
            optionsMenu = menu
            optionsMenu?.let { menu ->
                for (menuAdapter in menuItemAdapters) {
                    notifyMenuItem(menu, menuAdapter)
                }
            }
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val selected = menuItemAdapters.find { it.first == item.itemId }
        selected?.let {
            selected.second.onClick()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        runtime.stop()
        super.onDestroy()
    }
    abstract fun init(): Pair<Model, List<() -> Msg>>
    abstract fun update(model: Model, message: Msg): Pair<Model, List<() -> Msg>>
    abstract fun view(model: Model, dispatch: (Msg) -> Unit)
    abstract fun getLayout(): Int
    open fun getOptionsMenu(): Int? = null
    open fun onViewCreated() {}

    fun <T : Msg>registerAction(action: Action<T>) {
        actions.add(action)
    }

    fun <T : View> registerViewAdapter(resId: Int, viewAdapter: ViewAdapter<T>) {
        viewAdapters.add(Pair(resId, viewAdapter))
        if (viewCreated) viewAdapter.onCreated(findViewById(resId))
    }

    fun registerMenuItemAdapter(resId: Int, adapter: OptionsMenuItemAdapter) {
        menuItemAdapters.add(Pair(resId, adapter))
        optionsMenu?.let { notifyMenuItem(it, Pair(resId, adapter)) }
    }

    private fun notifyMenuItem(menu: Menu, menuItemAdapter: Pair<Int, OptionsMenuItemAdapter>) {
        menu.findItem(menuItemAdapter.first)?.let { menuItem -> menuItemAdapter.second.onCreated(menuItem) }
    }
}