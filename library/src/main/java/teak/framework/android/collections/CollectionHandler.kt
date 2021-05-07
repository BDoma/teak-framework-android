package teak.framework.android.collections

open class CollectionHandler<T>(
    private val comparator: Comparator<T>,
    private val insertCallback: (T) -> Unit,
    private val removeCallback: (T) -> Unit,
    private val updateCallback: (T) -> Unit
) {
    protected val items = arrayListOf<T>()

    open fun handle(newItems: Collection<T>) {

        val iterator = items.iterator()
        while (iterator.hasNext()){
            val item = iterator.next()
            if (newItems.none { comparator.itemsAreSame(item, it) }) {
                removeCallback(item)
                iterator.remove()
            }
        }

        for (newItem in newItems) {
            val oldItem = items.find { comparator.itemsAreSame(newItem, it) }
            if (oldItem == null) {
                insertCallback(newItem)
                items.add(newItem)
            } else if (!comparator.contentIsSame(oldItem, newItem)) {
                updateCallback(newItem)
                items[items.indexOf(oldItem)] = newItem
            }
        }
    }
}