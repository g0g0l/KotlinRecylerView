package com.example.bibaswann.infinitescrolling

import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var loading: Boolean = false

    val shownInOneScreen=20

    lateinit var adapter: RecyclerAdapter
    lateinit var items: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var mLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        rvMain.layoutManager = mLayoutManager


        items = ArrayList<String>()
        for (i in 1..shownInOneScreen) {
            items.add("Item: $i")
        }

        //This is how to implement anonymous class in Kotlin
        adapter = RecyclerAdapter(items, object : RecyclerAdapter.MyOnClickListener {
            override fun onClick(inputData: String) {
                //this@MainActivity is equivalent to MainActivity.this in Java
                Toast.makeText(this@MainActivity, "You clicked $inputData", Toast.LENGTH_LONG).show()
            }
        })
        rvMain.adapter = adapter

        rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = mLayoutManager.childCount
                    totalItemCount = mLayoutManager.itemCount
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                        //Probably here make a network call to add more data
                        if (!loading) {
                            //This will prevent loading when already is loading
                            //Otherwise there might be problem with ordering of items
                            loading = true
                            progressBar.visibility = View.VISIBLE
                            Handler().postDelayed({
                                for (i in totalItemCount + 1..totalItemCount + shownInOneScreen) {
                                    items.add("Item: $i")
                                }
                                adapter.notifyDataSetChanged()
                                progressBar.visibility = View.GONE
                                loading = false
                            }, 1000)
                        }
                    }
                }
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            // Refresh items
            getNewItems()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    fun getNewItems() {
        items.clear()
        for (i in 1..10) {
            items.add("New Item: $i")
        }
        adapter.notifyDataSetChanged()
    }
}
