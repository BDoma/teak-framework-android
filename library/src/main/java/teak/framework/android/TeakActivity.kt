package teak.framework.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import teak.framework.core.TeakComponent
import teak.framework.core.TeakComponentContract
import android.view.View

@Deprecated("Deprecated since 05.0 - Use TeakViewModel Instead")
abstract class TeakActivity<Model : Any, Msg : Any> : AppCompatActivity(), TeakComponentContract.Impl<Model, Msg> {
    protected lateinit var teaComponent: TeakComponent<Model, Msg>
    protected val viewLifecycle = ViewLifecycle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teaComponent = TeakComponent(this)
        teaComponent.onCreate()
        setContentView(getContentView())
        viewLifecycle.onViewCreated()
    }

    override fun onDestroy() {
        viewLifecycle.destroy()
        teaComponent.onDestroy()
        super.onDestroy()
    }

    abstract fun getContentView(): View

    inner class ViewLifecycle {
        private val viewRelatedFunctions = arrayListOf<() -> Unit>()
        private var isViewCreated = false

        fun runWhenViewCreated(function: () -> Unit) {
            if (isViewCreated) function()
            else viewRelatedFunctions.add(function)
        }

        internal fun onViewCreated() {
            isViewCreated = true
            viewRelatedFunctions.forEach { it() }
            viewRelatedFunctions.clear()
        }

        internal fun destroy() {
            isViewCreated = false
        }
    }
}