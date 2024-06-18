package teak.framework.android

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import teak.framework.core.TeakComponent
import teak.framework.core.TeakComponentContract

@Deprecated("Deprecated since 05.0 - Use TeakViewModel Instead")
abstract class TeakFragment<Model : Any, Msg : Any> : Fragment(), TeakComponentContract.Impl<Model, Msg> {
    protected lateinit var teaComponent: TeakComponent<Model, Msg>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        teaComponent = TeakComponent(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teaComponent.onCreate()
    }

    override fun onDestroyView() {
        teaComponent.onDestroy()
        super.onDestroyView()
    }
}