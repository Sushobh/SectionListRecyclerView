package com.example.myapplication

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class Section(protected val changeListener: ChangeListener) {

    interface ChangeListener {
        fun onRangeRemoved(startIndex: Int, count: Int)
        fun onRangeAdded(startIndex: Int, count: Int)
        fun onItemChanged(index: Int)
        fun isViewTypeNumberAvailable(viewTypeNumber: Int): Boolean
    }

    open class Root {

    }

    private val items = arrayListOf<Any>()
    abstract fun getViewTypes(): Set<Int>
    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int)

    fun <T : Root> addRoot(root: T) {
        if(items.size >= 1){
            items.removeAt(0)
        }
        items.add(0, root)
        changeListener.onItemChanged(0)
    }

    fun addItems(itemsToAdd: List<Any>, indexAt: Int): Boolean {
        if(indexAt == 0 || indexAt > getLength()){
            return false
        }
        if (items.size == 0 || !(items[0] is Root)) {
            return false
        } else {
            items.addAll(indexAt, itemsToAdd)
            changeListener.onRangeAdded(indexAt, itemsToAdd.size)
            return true
        }
    }


    fun removeItems(fromIndex: Int, count: Int) : Boolean{
        if(fromIndex + count > getLength()){
            return false
        }
        var removed = 0
        while (removed < count) {
            items.removeAt(fromIndex)
            removed++
        }
        changeListener.onRangeRemoved(fromIndex, count)
        return true
    }

    fun getLength(): Int {
        return items.size
    }

}
