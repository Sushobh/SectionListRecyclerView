package com.example.myapplication

import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ASection(changeListener: ChangeListener) : GroupedSection(changeListener) {

    class ASectionRoot : Root() {
         var isLoading = false
    }
    val root = ASectionRoot()
    private val groupedData = arrayListOf<GroupData>()

    val group1 = GroupData(
        "India", arrayListOf(
            ChildData("Bengaluru"), ChildData("Delhi"), ChildData(
                "Chennai"
            )
        )
    )
    val group2 = GroupData(
        "Pakistan", arrayListOf(
            ChildData("Karachi"), ChildData("Islamabad"), ChildData(
                "Quetta"
            )
        )
    )

    val group3 = GroupData(
        "Lanka", arrayListOf()
    )


    init {
        groupedData.add(group1)
        groupedData.add(group2)
        groupedData.add(group3)
        setData(groupedData,root)
    }

    override fun getChildrenForPosition(groupPosition: Int): List<Any> {
        return groupedData[groupPosition].children
    }


    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            1 -> {
                LoaderScreenViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.row_progressbar,
                        parent,
                        false
                    )
                )
            }
            2 -> {
                GroupViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.row_header,
                        parent,
                        false
                    )
                )
            }
            3 -> {
                ChildViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.row_child,
                        parent,
                        false
                    )
                )
            }
            else -> {
                return object : RecyclerView.ViewHolder(View(parent.context)) {}
            }
        }

    }

    override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
         val data = getItem(position)
         when(viewHolder){
             is LoaderScreenViewHolder -> {
                 if (data is ASectionRoot) {
                     if (data.isLoading) {
                         viewHolder.itemView.visibility = View.VISIBLE
                         viewHolder.itemView.setLayoutParams(RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                             RecyclerView.LayoutParams.WRAP_CONTENT))
                     } else {
                         viewHolder.itemView.visibility = View.GONE
                         viewHolder.itemView.setLayoutParams(RecyclerView.LayoutParams(0, 0))
                     }
                 }
             }

             is GroupViewHolder -> {
                 if (data is GroupData) {
                     viewHolder.textView.text = data.text
                 }
             }

             is ChildViewHolder -> {
                 if (data is ChildData) {
                     viewHolder.textView.text = data.text
                 }
             }
         }
    }

    override fun getViewType(position: Int) : Int?{
        return when(getItem(position)){
            is Root -> {
                1
            }
            is GroupData -> {
                2
            }

            is ChildData -> {
                3
            }
            else -> null
        }


    }

    override fun getViewTypes(): Set<Int> {
        return hashSetOf(1, 2, 3)
    }


}