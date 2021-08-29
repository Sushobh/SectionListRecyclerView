package com.example.myapplication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroupViewHolder(val changeListener : Section.ChangeListener, view : View) : SectionListAdapter.PartOfSectionViewHolder(changeListener,view){

    val textView = view.findViewById<TextView>(R.id.textview)

    init {
        view.setOnClickListener {
            toggleExpansion()
        }
    }

    fun toggleExpansion(){
        val section = changeListener.findSectionForPosition(adapterPosition)
        if(section is GroupedSection){
              section.toggleGroupFromAdapterPosition(adapterPosition)
        }
    }
}

class ChildViewHolder(changeListener : Section.ChangeListener, view : View) : SectionListAdapter.PartOfSectionViewHolder(changeListener,view){
    val textView = view.findViewById<TextView>(R.id.textview)
    init {
        view.setOnClickListener {
            val item = findItemAtPosition()

            toString()
        }
    }
}





data class GroupData(val text  : String, val children : ArrayList<ChildData>)

data class ChildData(val text : String)