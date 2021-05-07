package teak.framework.android.collections

class OrderedCollectionHandler<T>(
    private val comparator: Comparator<T>,
    insertCallback: (T) -> Unit,
    removeCallback: (T) -> Unit,
    updateCallback: (T) -> Unit,
    private val swapCallback: (Int, Int) -> Unit
) : CollectionHandler<T>(comparator, insertCallback, removeCallback, updateCallback) {

    override fun handle(newItems: Collection<T>) {
        super.handle(newItems)
        for ((index, newItem) in newItems.withIndex()) {
            if (!comparator.itemsAreSame(items[index], newItem)){
                var i = index + 1
                while (!comparator.itemsAreSame(items[i], newItem))
                    i++

                items[i] = items[index]
                items[index] = newItem
                swapCallback(index, i)
            }
        }
    }
}