package com.tea.kotlin.android

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.tea.kotlin.Runtime
import com.tea.kotlin.android.lifecycles.Lifecycle
import com.tea.kotlin.helper.Initializer
import com.tea.kotlin.helper.Updater


abstract class Activity<Model : Any, Msg> : AppCompatActivity() {
    private lateinit var runtime: Runtime<Model, Msg>
    private lateinit var updater : Updater<Model, Msg>
    private lateinit var initer : Initializer<Model, Msg>
    val lifecycleOwner = Lifecycle<Msg>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updater = updater()
        initer = initializer()
        runtime = Runtime(::init, view = ::myView, ::update)
        setContentView(getLayout())
        lifecycleOwner.onViewCreated(findViewById(android.R.id.content))
    }

    private fun init(): Pair<Model, List<() -> Msg>> {
        return initer.init()
    }

    private fun update(model: Model, message: Msg): Pair<Model, List<() -> Msg>> {
        return updater.update(model, message)
    }

    private fun myView(model: Model, dispatch: (Msg) -> Unit){
        lifecycleOwner.addDispatch(dispatch)
        view(model, dispatch)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getOptionsMenu()?.let {
            val inflater = menuInflater
            inflater.inflate(it, menu)
            menu?.let { lifecycleOwner.onOptionsMenuCreated(menu) }
            return true
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (lifecycleOwner.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        runtime.stop()
        super.onDestroy()
    }

    abstract fun initializer(): Initializer<Model, Msg>
    abstract fun view(model: Model, dispatch: (Msg) -> Unit)
    abstract fun updater() : Updater<Model, Msg>
    abstract fun getLayout(): Int
    open fun getOptionsMenu(): Int? = null
}