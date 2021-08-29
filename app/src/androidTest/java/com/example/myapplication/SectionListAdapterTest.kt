package com.example.myapplication

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import java.lang.Exception

class SectionListAdapterTest {

    val root = object : Section.Root() {

    }
    val changeListener = object : Section.ChangeListener {
        override fun onRangeRemoved(startIndex: Int, count: Int) {

        }

        override fun onRangeAdded(startIndex: Int, count: Int) {

        }

        override fun onItemChanged(index: Int) {

        }

        override fun isViewTypeNumberAvailable(viewTypeNumber: Int): Boolean {
            return false
        }

    }


    class DummySection(changeListener: ChangeListener) : Section(changeListener){


        init {
            addRoot(object : Root() {

            })
        }

        override fun getViewTypes(): Set<Int> {

            return hashSetOf()
        }

        override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            throw Exception("")
        }

        override fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        }

    }

    @Test
    fun test(){
        val section1 = DummySection(changeListener)
        val section2 = DummySection(changeListener)
        val section3 = DummySection(changeListener)
        val section4 = DummySection(changeListener)
        val sectionListAdapter = SectionListAdapter(arrayListOf(section1,section2,section3,section4))
        Assert.assertEquals(section3,sectionListAdapter.findSectionForPosition(2))
        Assert.assertEquals(null,sectionListAdapter.findSectionForPosition(4))
    }

    @Test
    fun test1(){
        val section1 = DummySection(changeListener)
        val section2 = DummySection(changeListener)
        val section3 = DummySection(changeListener)
        val section4 = DummySection(changeListener)
        val sectionListAdapter = SectionListAdapter(arrayListOf(section1,section2,section3,section4))

        //Check 1
        section2.addItems(getDummyList(5),1)
        Assert.assertEquals(section1,sectionListAdapter.findSectionForPosition(0))
        for(i in 1..6){
            Assert.assertEquals(section2,sectionListAdapter.findSectionForPosition(i))
        }
        Assert.assertEquals(section3,sectionListAdapter.findSectionForPosition(7))
        Assert.assertEquals(section4,sectionListAdapter.findSectionForPosition(8))
        Assert.assertEquals(0,sectionListAdapter.findStartIndexForSection(section1))
        Assert.assertEquals(1,sectionListAdapter.findStartIndexForSection(section2))
        Assert.assertEquals(7,sectionListAdapter.findStartIndexForSection(section3))
        Assert.assertEquals(8,sectionListAdapter.findStartIndexForSection(section4))


        //Check 2
        section1.addItems(getDummyList(10),1)
        for(i in 0..10){
            Assert.assertEquals(section1,sectionListAdapter.findSectionForPosition(i))
        }
        for(i in 11..16){
            Assert.assertEquals(section2,sectionListAdapter.findSectionForPosition(i))
        }
        Assert.assertEquals(section3,sectionListAdapter.findSectionForPosition(17))
        Assert.assertEquals(section4,sectionListAdapter.findSectionForPosition(18))
        Assert.assertEquals(0,sectionListAdapter.findStartIndexForSection(section1))
        Assert.assertEquals(11,sectionListAdapter.findStartIndexForSection(section2))
        Assert.assertEquals(17,sectionListAdapter.findStartIndexForSection(section3))
        Assert.assertEquals(18,sectionListAdapter.findStartIndexForSection(section4))

        //Check 3
        section1.removeItems(5,6)
        for(i in 0..4){
            Assert.assertEquals(section1,sectionListAdapter.findSectionForPosition(i))
        }
        for(i in 5..10){
            Assert.assertEquals(section2,sectionListAdapter.findSectionForPosition(i))
        }
        Assert.assertEquals(section3,sectionListAdapter.findSectionForPosition(11))
        Assert.assertEquals(section4,sectionListAdapter.findSectionForPosition(12))
        Assert.assertEquals(0,sectionListAdapter.findStartIndexForSection(section1))
        Assert.assertEquals(5,sectionListAdapter.findStartIndexForSection(section2))
        Assert.assertEquals(11,sectionListAdapter.findStartIndexForSection(section3))
        Assert.assertEquals(12,sectionListAdapter.findStartIndexForSection(section4))
    }



    fun getDummyList(count : Int) : List<Any>{
        val list = arrayListOf<Any>()
        for(i in 0 until count){
            list.add(Any())
        }
        return list
    }

}