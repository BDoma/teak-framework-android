package teak.framework.android.lifecycles

class ViewLifecycle {
    private val viewRelatedFunctions = arrayListOf<() -> Unit>()
    private var isViewCreated = false

    internal fun onViewCreated() {
        isViewCreated = true
        viewRelatedFunctions.forEach { it() }
        viewRelatedFunctions.clear()
    }

    fun runWhenViewCreated(function: () -> Unit) {
        if (isViewCreated) function()
        else viewRelatedFunctions.add(function)
    }

    fun destroy() {
        isViewCreated = false
    }
}