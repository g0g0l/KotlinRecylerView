package com.example.bibaswann.infinitescrolling

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

//In Kotlin, we can directly create and assign variables in constructor
class RecyclerAdapter(val numberList: ArrayList<String>, val myOnClickListener: MyOnClickListener) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    interface MyOnClickListener {
        fun onClick(inputData: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_view, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.bindItems(numberList[position], myOnClickListener)
    }

    override fun getItemCount(): Int {
        return numberList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(string: String, myOnClickListener: MyOnClickListener) {
            val tvSerial = itemView.findViewById(R.id.tvSerial) as TextView
            val button = itemView.findViewById(R.id.button) as Button
            tvSerial.text = string

            button.setOnClickListener { myOnClickListener.onClick(string) }
        }
    }
}