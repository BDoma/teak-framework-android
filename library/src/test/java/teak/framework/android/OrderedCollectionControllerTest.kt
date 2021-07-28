package teak.framework.android

import org.junit.Test
import org.mockito.Mockito
import teak.framework.android.dummy.models.DummyItem

class OrderedCollectionControllerTest {
    @Test
    fun testOrderDoesMatter() {
        val item1 = DummyItem(1, "a")
        val item2 = DummyItem(2, "b")
        val notifier = Mockito.mock(DummyOrderedCollectionHandler.Notifier::class.java)
        val collectionHandler = DummyOrderedCollectionHandler(notifier)
        collectionHandler.applyChanges(listOf(item1, item2))
        collectionHandler.applyChanges(listOf(item2, item1))

        Mockito.verify(notifier).insert(item1)
        Mockito.verify(notifier).insert(item2)

        Mockito.verify(notifier, Mockito.never()).remove(item1)
        Mockito.verify(notifier, Mockito.never()).remove(item2)

        Mockito.verify(notifier, Mockito.never()).update(item1)
        Mockito.verify(notifier, Mockito.never()).update(item2)

        Mockito.verify(notifier).swap(0, 1)
    }
}