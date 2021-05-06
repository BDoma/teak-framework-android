package teak.framework.android.collections

abstract class CollectionHandler<T>(private val comparator: Comparator<T>) {
    private var items = ArrayList<T>()

    protected abstract fun insert(item: T)
    protected abstract fun remove(item: T)
    protected abstract fun update(item: T)

    fun handle(newItems: Collection<T>) {
        for (item in items) {
            if (newItems.none { comparator.itemsAreSame(item, it) }) remove(item)
        }

        for (newItem in newItems) {
            val oldItem = items.find { comparator.itemsAreSame(newItem, it) }
            if (oldItem == null) insert(newItem)
            else if (!comparator.contentIsSame(oldItem, newItem)) {
                update(newItem)
            }
        }
        items.clear()
        items.addAll(newItems)
    }
}