package com.tea.kotlin.android

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.tea.kotlin.Runtime
import com.tea.kotlin.android.lifecycles.Lifecycle


abstract class Fragment<Model : Any, Msg> : Fragment() {
    private lateinit var runtime: Runtime<Model, Msg>
    val lifecycleOwner = Lifecycle<Msg>()

    override fun onCreate(savedInstanceState: Bundle?) {
        getOptionsMenu()?.let { setHasOptionsMenu(true) }
        super.onCreate(savedInstanceState)
        runtime = Runtime(::init, view = ::myView, ::update)
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
        onViewCreated()
    }

    private fun myView(model: Model, dispatch: (Msg) -> Unit){
        lifecycleOwner.addDispatch(dispatch)
        view(model, dispatch)
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

    abstract fun init(): Pair<Model, List<() -> Msg>>
    abstract fun update(model: Model, message: Msg): Pair<Model, List<() -> Msg>>
    abstract fun view(model: Model, dispatch: (Msg) -> Unit)
    abstract fun getLayout(): Int
    open fun getOptionsMenu(): Int? = null
    open fun onViewCreated() {}
}