package teak.framework.android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import teak.framework.core.TeakComponent
import teak.framework.core.TeakComponentContract


abstract class Fragment<Model : Any, Msg : Any> : Fragment(), TeakComponentContract.Impl<Model, Msg> {
    protected lateinit var teaComponent: TeakComponent<Model, Msg>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teaComponent = TeakComponent(this)
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
        teaComponent.onCreate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        teaComponent.onDestroy()
    }

    abstract fun getLayout(): Int
}