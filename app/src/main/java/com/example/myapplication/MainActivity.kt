package com.example.myapplication

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R.*


class MainActivity : AppCompatActivity() {

    lateinit var sectionAdapter : SectionListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.list_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        sectionAdapter = SectionListAdapter()
        val section = ASection(sectionAdapter)
        val section1 = ASection(sectionAdapter)

        sectionAdapter.sections = arrayListOf(section,section1)
        recyclerView.adapter = sectionAdapter

    }

    fun postDelayed(time : Long,callback : () -> Unit){
        Handler(Looper.getMainLooper()).postDelayed(callback,time)
    }
}