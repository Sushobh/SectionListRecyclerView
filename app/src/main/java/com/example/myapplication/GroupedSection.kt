package com.example.myapplication

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class GroupedSection(val listener: ChangeListener) : Section(listener){





    private val groupPositions = hashMapOf<Int,Int>()
    private val groupsData = arrayListOf<Any>()
    private val childrenMap = hashMapOf<Any,List<Any>>()
    private val expansionStates = hashMapOf<Int,Boolean>()
    abstract fun getChildrenForPosition(groupPosition : Int) : List<Any>




    fun setData(groups : List<Any>){

        groupsData.addAll(groups)
        groups.forEachIndexed {position,item ->
            childrenMap.put(item,getChildrenForPosition(position))
        }
        parseGroups()
    }

    private fun parseGroups(){
        var groupCurrentIndex = 0
        val data = arrayListOf<Any>()
        groupsData.forEachIndexed { pos,item ->
            data.add(item)
            groupPositions.put(pos,groupCurrentIndex)
            val children = getChildrenOfGroup(item)
            data.addAll(children)
            groupCurrentIndex = groupCurrentIndex + children.size + 1
            expansionStates.put(pos,true)
        }
        addItems(data,0)
    }

    fun isExpanded(groupPosition : Int) : Boolean {
        return expansionStates.getOrDefault(groupPosition,true)
    }

    fun getChildrenOfGroup(groupedItem: Any) : List<Any>{
        return childrenMap[groupedItem] ?: arrayListOf()
    }

    fun collapseGroup(groupPosition: Int){
        if(!isExpanded(groupPosition)) return
        val groupAtPosition = groupsData[groupPosition]
        val groupFlatIndex = groupPositions[groupPosition]
        val childrenOfGroup = getChildrenOfGroup(groupAtPosition)
        val childCount = childrenOfGroup.size
        if(childCount == 0){
            return
        }
        else {
            groupFlatIndex?.let {
                removeItems(groupFlatIndex+1,childrenOfGroup.size)
            }
            expansionStates.put(groupPosition,false)
        }
    }

    fun expandGroup(groupPosition: Int){
        if(isExpanded(groupPosition)) return
        val groupAtPosition = groupsData[groupPosition]
        val childrenOfGroup = getChildrenOfGroup(groupAtPosition)
        val childCount = childrenOfGroup.size
        val groupFlatIndex = groupPositions[groupPosition]
        if(childCount == 0){
            return
        }
        else {
            groupFlatIndex?.let {
                addItems(getChildrenOfGroup(groupAtPosition),groupFlatIndex+1)
            }
            expansionStates.put(groupPosition,true)
        }
    }

    fun toggleGroupFromAdapterPosition(adapterPosition : Int){
        val internalPosition = changeListener.getPositionInsideSection(adapterPosition,this)
        groupPositions.entries.forEach {
            if(it.value == internalPosition){
                if(isExpanded(it.key)){
                    collapseGroup(it.key)
                }
                else {
                    expandGroup(it.key)
                }
            }
        }
    }

}