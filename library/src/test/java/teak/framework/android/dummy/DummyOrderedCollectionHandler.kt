package teak.framework.android.dummy

import teak.framework.android.collections.OrderedCollectionHandler
import teak.framework.android.dummy.models.DummyItem

class DummyOrderedCollectionHandler(private val notifier: Notifier) {
    private val handler = OrderedCollectionHandler(DummyItem.Comparator(), ::insert, ::remove, ::update, ::swap)

    fun applyChanges(newItems: Collection<DummyItem>) {
        handler.handle(newItems)
    }

    private fun insert(item: DummyItem) {
        notifier.insert(item)
    }

    private fun remove(item: DummyItem) {
        notifier.remove(item)
    }

    private fun update(item: DummyItem) {
        notifier.update(item)
    }

    private fun swap(pos1: Int, pos2: Int) {
        notifier.swap(pos1, pos2)
    }

    interface Notifier {
        fun insert(item: DummyItem)
        fun remove(item: DummyItem)
        fun update(item: DummyItem)
        fun swap(pos1: Int, pos2: Int)
    }
}