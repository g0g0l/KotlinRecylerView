package com.example.bibaswann.infinitescrolling

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {
    var pastVisiblesItems: Int = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0

    lateinit var adapter:RecyclerAdapter
    lateinit var items: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        var mLayoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rvMain.layoutManager = mLayoutManager;


        items = ArrayList<String>()
        for (i in 1..10) {
            items.add("Item: $i")
        }

        adapter = RecyclerAdapter(items)
        rvMain.adapter = adapter

        rvMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = mLayoutManager.childCount
                    totalItemCount = mLayoutManager.itemCount
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition()
                    if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                        for (i in totalItemCount+1..totalItemCount+10) {
                            items.add("Item: $i")
                        }
                        adapter.notifyDataSetChanged()
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

    fun getNewItems()
    {
        items.clear()
        for (i in 1..10) {
            items.add("New Item: $i")
        }
        adapter.notifyDataSetChanged()
    }
}
