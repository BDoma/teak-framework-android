package teak.framework.android.collections.models

interface Comparator<T> {
    fun itemsAreSame(first: T, second: T): Boolean
    fun contentIsSame(first: T, second: T): Boolean
}