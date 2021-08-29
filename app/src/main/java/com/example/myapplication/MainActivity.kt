package com.example.myapplication

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import com.example.myapplication.R.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        val list_view = findViewById<MyListView>(R.id.list_view)
        val scrollView = findViewById<ScrollView>(R.id.scroll_view)
        val linearView = findViewById<LinearLayout>(R.id.linear_view)
        val data = arrayListOf<String>()
        for(i in 0..99){
            data.add(getRandomString(6))
        }
        list_view.adapter = MyAdapter(data)

        scrollView.setOnScrollChangeListener { view: View, i: Int, i1: Int, i2: Int, i3: Int ->
            list_view.children.iterator()
                    .forEachRemaining {
                        if(it.tag != null && it.tag.equals("TopView")){
                            Log.i("BOOM","${it.y} is y")
                            val localVisibleRect = Rect()
                            val globalRect = Rect()
                            val itemLocalRect = Rect()
                            val itemGlobalRect = Rect()
                            linearView.getLocalVisibleRect(localVisibleRect)
                            linearView.getGlobalVisibleRect(globalRect)
                            it.getLocalVisibleRect(itemLocalRect)
                            it.getGlobalVisibleRect(itemGlobalRect)
                            Log.i("Rectlocal",localVisibleRect.toShortString())
                            Log.i("Rectglobal",globalRect.toShortString())
                            Log.i("Rect1local",itemLocalRect.toShortString())
                            Log.i("Rect1global",itemGlobalRect.toShortString())
                            Log.i("Rect","Height is ${it.height}")
                            Log.i("Rect","--------------------------------------------")
                        }

                    }
        }



    }

    class MyAdapter(val data : ArrayList<String>) : BaseAdapter() {
        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(p0: Int): Any {
            return data.get(p0)
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
           val text = data.get(p0)
           val layout  = LayoutInflater.from(p2!!.context)
                   .inflate(R.layout.simple_text_view,null)
            val textView = layout.findViewById<TextView>(R.id.text_view)
            textView.text = text
            if(p0 == 0){
                layout.tag = "TopView"
            }
            return layout
        }

    }

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
                .map { allowedChars.random() }
                .joinToString("")
    }

}