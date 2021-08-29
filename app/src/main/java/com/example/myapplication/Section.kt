package com.example.myapplication

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


abstract class Section(protected val changeListener: ChangeListener) {

    interface ChangeListener {
        fun onRangeRemoved(startIndex: Int, count: Int,section : Section)
        fun onRangeAdded(startIndex: Int, count: Int,section : Section)
        fun onItemChanged(startIndex: Int,section : Section)
        fun isViewTypeNumberAvailable(viewTypeNumber: Int,section : Section): Boolean
        fun getPositionInsideSection(adapterPosition : Int,section: Section) : Int?
        fun findSectionForPosition(adapterPosition: Int) : Section?
    }



    private val items = arrayListOf<Any>()
    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int)
    abstract fun getViewType(position : Int)
            : Int?
    abstract fun getViewTypes() : Set<Int>





    fun addItems(itemsToAdd: List<Any>, indexAt: Int): Boolean {
        if(indexAt > getLength()){
            return false
        }
        items.addAll(indexAt, itemsToAdd)
        changeListener.onRangeAdded(indexAt, itemsToAdd.size,this)
        return true
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
        changeListener.onRangeRemoved(fromIndex, count,this)
        return true
    }

    fun getLength(): Int {
        return items.size
    }

    fun getItem(position : Int) : Any? {
        return items.get(position)
    }

    fun getItemAtAdapterPosition(adapterPosition : Int) : Any? {
        val pos = changeListener.getPositionInsideSection(adapterPosition,this)
        pos?.let {
            if(pos < items.size){
                return items[pos]
            }
        }
        return null
    }

}
