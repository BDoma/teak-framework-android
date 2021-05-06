package teak.framework.android.collections

class CollectionHandlerImpl<T>(
    comparator: Comparator<T>,
    private val insertCallback: (T) -> Unit,
    private val removeCallback: (T) -> Unit,
    private val updateCallback: (T) -> Unit
) : CollectionHandler<T>(comparator) {
    override fun insert(item: T) {
        insertCallback(item)
    }

    override fun remove(item: T) {
        removeCallback(item)
    }

    override fun update(item: T) {
        updateCallback(item)
    }
}