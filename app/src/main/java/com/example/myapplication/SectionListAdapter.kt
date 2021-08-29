package com.example.myapplication

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SectionListAdapter(val sections : List<Section>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return sections.find {
            it.getViewTypes().contains(viewType)
        }?.getViewHolder(parent,viewType) ?: object : RecyclerView.ViewHolder(View(parent.context)) {}

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

}