package com.example.myapplication

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SectionListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(),Section.ChangeListener{

    var sections = arrayListOf<Section>()


    override fun getItemViewType(position: Int): Int {
        val sectionForPosition = findSectionForPosition(position)
        sectionForPosition?.let { section ->
            val startIndex = findStartIndexForSection(section)
            startIndex?.let { it ->
                val viewType = section.getViewType(position-it)
                return viewType ?: 0
            }
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val section = sections.find {
            it.getViewTypes().contains(viewType)
        }
        val viewHolder = section?.getViewHolder(parent,viewType)
        return viewHolder ?: object : RecyclerView.ViewHolder(View(parent.context)) {}
    }

    fun findSectionForPosition(position: Int) : Section? {
        var currentLength = 0
        sections.forEach {
            val lastIndex = currentLength + (it.getLength())
            if(position < lastIndex){
                return it
            }
            currentLength = currentLength + it.getLength()
        }
        return null
    }

    fun findStartIndexForSection(section : Section) : Int?{
        var currentLength = 0
        sections.forEach {
            if(it == section){
                return currentLength
            }
            else {
                currentLength = currentLength + it.getLength()
            }
        }
        return null
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val section = findSectionForPosition(position)
        section?.let {
            val sectionStartPosition = findStartIndexForSection(it)
            sectionStartPosition?.let {
                val sectionInternalPosition = position-sectionStartPosition
                section.bindViewHolder(holder,sectionInternalPosition)
            }

        }
    }

    override fun getItemCount(): Int {
        var count = 0
        sections.forEach {
            count = count + it.getLength()
        }
        return count
    }

    override fun onRangeRemoved(startIndex: Int, count: Int, section: Section) {
        val index = findStartIndexForSection(section)
        if(index != null){
            notifyItemRangeRemoved(index+startIndex,count)
        }
    }

    override fun onRangeAdded(startIndex: Int, count: Int, section: Section) {
        val index = findStartIndexForSection(section)
        if(index != null){
            notifyItemRangeInserted(index+startIndex,count)
        }
    }

    override fun onItemChanged(startIndex: Int, section: Section) {
        val index = findStartIndexForSection(section)
        if(index != null){
            notifyItemChanged(index+startIndex)
        }
    }

    override fun isViewTypeNumberAvailable(viewTypeNumber: Int, section: Section): Boolean {
        TODO("Not yet implemented")
    }


}