package com.tea.kotlin.android

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.tea.kotlin.Runtime
import com.tea.kotlin.android.lifecycles.Lifecycle
import com.tea.kotlin.android.lifecycles.SimpleLifecycle
import com.tea.kotlin.helper.Initializer
import com.tea.kotlin.helper.Updater


abstract class Fragment<Model : Any, Msg> : Fragment() {
    private lateinit var updater : Updater<Model, Msg>
    private lateinit var initer : Initializer<Model, Msg>
    private lateinit var runtime: Runtime<Model, Msg>
    val lifecycleOwner = Lifecycle<Msg>()
    val simplelifecycleOwner = SimpleLifecycle<Msg>()

    override fun onCreate(savedInstanceState: Bundle?) {
        getOptionsMenu()?.let { setHasOptionsMenu(true) }
        super.onCreate(savedInstanceState)
        updater = updater()
        initer = initializer()
        runtime = Runtime(::init, view = ::view, ::update)
    }

    private fun init(): Pair<Model, List<() -> Msg>> {
        return initer.init()
    }

    private fun update(model: Model, message: Msg): Pair<Model, List<() -> Msg>> {
        return updater.update(model, message)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleOwner.onViewCreated(view)
        simplelifecycleOwner.onViewCreated()
        onViewCreated()
    }

    private fun view(model: Model, dispatch: (Msg) -> Unit){
        lifecycleOwner.addDispatch(dispatch)
        simplelifecycleOwner.addDispatch(dispatch)
        refreshView(model)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        getOptionsMenu()?.let {
            inflater.inflate(it, menu)
            lifecycleOwner.onOptionsMenuCreated(menu)
        }
        super.onCreateOptionsMenu(menu, inflater)
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
    abstract fun refreshView(model: Model)
    abstract fun updater() : Updater<Model, Msg>
    abstract fun getLayout(): Int
    open fun getOptionsMenu(): Int? = null
    open fun onViewCreated() {}
}