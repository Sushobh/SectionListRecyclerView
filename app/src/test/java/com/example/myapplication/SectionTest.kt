package com.example.myapplication

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import java.lang.Exception

class SectionTest {


    val changeListener = Mockito.mock(Section.ChangeListener::class.java)

    val root = object : Section.Root() {

    }



    class DummySection(changeListener: ChangeListener) : Section(changeListener){



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
    fun test1(){
        val dummySection = DummySection(changeListener)
        dummySection.addRoot(object : Section.Root() {

        })
        Assert.assertEquals(true,dummySection.addItems(getDummyList(5),1))
        Assert.assertEquals(6,dummySection.getLength())
        Assert.assertEquals(true,dummySection.addItems(getDummyList(3),6))
        Mockito.verify(changeListener,Mockito.times(1)).onRangeAdded(1,5)
        Mockito.verify(changeListener,Mockito.times(1)).onItemChanged(0)
        Mockito.verify(changeListener,Mockito.times(1)).onRangeAdded(6,3)
        Assert.assertEquals(9,dummySection.getLength())
    }

    /**
     * Test the length of section
     */
    @Test
    fun test12(){
        val dummySection = DummySection(changeListener)
        dummySection.addRoot(root)
        Assert.assertEquals(true,dummySection.addItems(getDummyList(5),1))
        Assert.assertEquals(true,dummySection.addItems(getDummyList(10),6))
        Assert.assertEquals(16,dummySection.getLength())
    }


    /**
     * Test that remove event is called on the listener
     */
    @Test
    fun test3(){
        val dummySection = DummySection(changeListener)
        dummySection.addRoot(object : Section.Root() {

        })
        dummySection.addItems(getDummyList(5),1)
        dummySection.addItems(getDummyList(4),6)
        dummySection.removeItems(7,3)
        Assert.assertEquals(7,dummySection.getLength())
        dummySection.removeItems(1,6)
        Assert.assertEquals(1,dummySection.getLength())
        Mockito.verify(changeListener,Mockito.times(1)).onRangeRemoved(7,3)
    }

    /**
     * Test that remove event fails when index out of bounds
     */
    @Test
    fun test5(){
        val dummySection = DummySection(changeListener)
        dummySection.addRoot(object : Section.Root() {

        })
        dummySection.addItems(getDummyList(5),1)
        dummySection.addItems(getDummyList(4),6)
        Assert.assertFalse(dummySection.removeItems(7,10))
        Assert.assertFalse(dummySection.removeItems(20,1))
        Mockito.verify(changeListener,Mockito.times(0)).onRangeRemoved(Mockito.anyInt(),Mockito.anyInt())
        Assert.assertEquals(10,dummySection.getLength())
    }


    /**
     * Test whether addition of items fails when attempting to add after last index
     */
    @Test
    fun test4(){
        val dummySection = DummySection(changeListener)
        dummySection.addRoot(root)
        Assert.assertTrue(dummySection.addItems(getDummyList(5),1))
        Assert.assertTrue(dummySection.addItems(getDummyList(4),6))
        Assert.assertFalse(dummySection.addItems(getDummyList(10),11))
        Assert.assertEquals(10,dummySection.getLength())
    }

    fun getDummyList(count : Int) : List<Any>{
        val list = arrayListOf<Any>()
        for(i in 0 until count){
            list.add(Any())
        }
        return list
    }

}