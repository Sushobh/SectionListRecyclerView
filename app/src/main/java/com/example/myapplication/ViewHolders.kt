package com.example.myapplication

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroupViewHolder(val view : View) : RecyclerView.ViewHolder(view){
    val textView = view.findViewById<TextView>(R.id.textview)

}

class ChildViewHolder(val view : View) : RecyclerView.ViewHolder(view){
    val textView = view.findViewById<TextView>(R.id.textview)
}

class LoaderScreenViewHolder(val view : View) : RecyclerView.ViewHolder(view) {

}

data class GroupData(val text  : String, val children : ArrayList<ChildData>)

data class ChildData(val text : String)