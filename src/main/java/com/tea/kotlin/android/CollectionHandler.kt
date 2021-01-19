package com.tea.kotlin.android

abstract class CollectionHandler<T> {
    private var items = ArrayList<T>()

    protected abstract fun itemsAreSame(first: T, second: T): Boolean
    protected abstract fun contentIsSame(first: T, second: T): Boolean
    protected abstract fun insert(item: T)
    protected abstract fun remove(item: T)
    protected abstract fun update(item: T)

    fun handle(newItems: Collection<T>) {
        for (item in items) {
            if (newItems.none { itemsAreSame(item, it) }) remove(item)
        }

        for (newItem in newItems) {
            val oldItem = items.find { itemsAreSame(newItem, it) }
            if (oldItem == null) insert(newItem)
            else if (!contentIsSame(oldItem, newItem)) {
                update(newItem)
            }
        }
        items.clear()
        items.addAll(newItems)
    }
}