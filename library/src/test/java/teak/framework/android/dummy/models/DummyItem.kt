package teak.framework.android.dummy.models

data class DummyItem(val id: Int, val content: String){
    class Comparator : teak.framework.android.collections.Comparator<DummyItem> {
        override fun itemsAreSame(first: DummyItem, second: DummyItem): Boolean {
            return first.id == second.id
        }

        override fun contentIsSame(first: DummyItem, second: DummyItem): Boolean {
            return first.id == second.id && first.content == second.content
        }

    }
}