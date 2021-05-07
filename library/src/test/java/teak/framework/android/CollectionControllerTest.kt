package teak.framework.android

import org.junit.Test
import org.mockito.Mockito
import teak.framework.android.dummy.DummyCollectionHandler
import teak.framework.android.dummy.DummyItem

class CollectionControllerTest {
    @Test
    fun testInsertItem() {
        val item1 = DummyItem(1, "a")
        val notifier = Mockito.mock(DummyCollectionHandler.Notifier::class.java)
        val collectionHandler = DummyCollectionHandler(notifier)
        collectionHandler.applyChanges(listOf(item1))
        Mockito.verify(notifier).insert(item1)
    }

    @Test
    fun testRemoveItem() {
        val item1 = DummyItem(1, "a")
        val notifier = Mockito.mock(DummyCollectionHandler.Notifier::class.java)
        val collectionHandler = DummyCollectionHandler(notifier)
        collectionHandler.applyChanges(listOf(item1))
        collectionHandler.applyChanges(listOf())
        Mockito.verify(notifier).remove(item1)
        collectionHandler.applyChanges(listOf())
        Mockito.verify(notifier, Mockito.times(1)).remove(item1)
    }

    @Test
    fun testUpdateItem() {
        val item1 = DummyItem(1, "a")
        val item1Updated = DummyItem(1, "b")
        val notifier = Mockito.mock(DummyCollectionHandler.Notifier::class.java)
        val collectionHandler = DummyCollectionHandler(notifier)
        collectionHandler.applyChanges(listOf(item1))
        collectionHandler.applyChanges(listOf(item1Updated))
        Mockito.verify(notifier).insert(item1)
        Mockito.verify(notifier).update(item1Updated)
    }

    @Test
    fun testOrderDoesNotMatter() {
        val item1 = DummyItem(1, "a")
        val item2 = DummyItem(2, "b")
        val notifier = Mockito.mock(DummyCollectionHandler.Notifier::class.java)
        val collectionHandler = DummyCollectionHandler(notifier)
        collectionHandler.applyChanges(listOf(item1, item2))
        collectionHandler.applyChanges(listOf(item2, item1))

        Mockito.verify(notifier).insert(item1)
        Mockito.verify(notifier).insert(item2)

        Mockito.verify(notifier, Mockito.never()).remove(item1)
        Mockito.verify(notifier, Mockito.never()).remove(item2)

        Mockito.verify(notifier, Mockito.never()).update(item1)
        Mockito.verify(notifier, Mockito.never()).update(item2)
    }
}