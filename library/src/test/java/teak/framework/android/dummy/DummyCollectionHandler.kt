package teak.framework.android.dummy

import teak.framework.android.collections.CollectionHandler

class DummyCollectionHandler(private val notifier: Notifier) {
    private val handler = CollectionHandler(DummyItem.Comparator(), ::insert, ::remove, ::update)

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

    interface Notifier {
        fun insert(item: DummyItem)
        fun remove(item: DummyItem)
        fun update(item: DummyItem)
    }
}