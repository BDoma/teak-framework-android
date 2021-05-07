package teak.framework.android.collections

import androidx.recyclerview.widget.RecyclerView
import teak.framework.android.collections.models.Comparator

abstract class RecyclerViewAdapter<T, VH : RecyclerView.ViewHolder>(
    private val comparator: Comparator<T>,
    protected val items: ArrayList<T> = arrayListOf(),
    orderIsImportant: Boolean = true
) : RecyclerView.Adapter<VH>() {

    private val handler = if (orderIsImportant)
        OrderedCollectionHandler(comparator, ::insert, ::remove, ::update, ::swap)
    else CollectionHandler(comparator, ::insert, ::remove, ::update)

    init {
        handler.handle(items)
    }

    fun changeItems(newItems: Collection<T>) {
        handler.handle(newItems)
    }

    private fun insert(item: T) {
        items.add(item)
        notifyItemInserted(items.lastIndex)
    }

    private fun remove(item: T) {
        items.find { comparator.itemsAreSame(it, item) }?.let {
            val index = items.indexOf(it)
            items.remove(it)
            notifyItemRemoved(index)
        }
    }

    private fun update(item: T) {
        items.find { comparator.itemsAreSame(it, item) }?.let {
            val index = items.indexOf(it)
            items[index] = item
            notifyItemChanged(index)
        }
    }

    private fun swap(pos1: Int, pos2: Int) {
        val first = items.removeAt(pos1)
        items.add(pos2, first)
        notifyItemMoved(pos1, pos2)

        val second = items.removeAt(pos2 - 1)
        items.add(pos1, second)
        notifyItemMoved(pos2 - 1, pos1)
    }

    override fun getItemCount(): Int = items.size
}