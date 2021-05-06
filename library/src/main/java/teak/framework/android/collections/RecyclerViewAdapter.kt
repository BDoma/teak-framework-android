package teak.framework.android.collections

import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewAdapter<T, VH : RecyclerView.ViewHolder>(
    private val comparator: Comparator<T>,
    protected val items: ArrayList<T> = arrayListOf()) : RecyclerView.Adapter<VH>() {

    private val handler = CollectionHandlerImpl(comparator, ::insert, ::remove, ::update)

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

    override fun getItemCount(): Int = items.size
}