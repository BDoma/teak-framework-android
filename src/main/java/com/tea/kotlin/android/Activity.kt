package com.tea.kotlin.android

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.tea.kotlin.Runtime
import com.tea.kotlin.android.common.Lifecycle


abstract class Activity<Model, Msg> : AppCompatActivity() {
    private lateinit var runtime: Runtime<Model, Msg>
    val lifecycleOwner = Lifecycle<Msg>()

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runtime = Runtime(::init, view = ::myView, ::update)
        setContentView(getLayout())
        lifecycleOwner.onViewCreated(findViewById(android.R.id.content))
        onViewCreated()
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

    abstract fun init(): Pair<Model, List<() -> Msg>>
    abstract fun update(model: Model, message: Msg): Pair<Model, List<() -> Msg>>
    abstract fun view(model: Model, dispatch: (Msg) -> Unit)
    abstract fun getLayout(): Int
    open fun getOptionsMenu(): Int? = null
    open fun onViewCreated() {}
}