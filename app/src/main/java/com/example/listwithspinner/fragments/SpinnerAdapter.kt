package com.example.listwithspinner.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.listwithspinner.R

class SpinnerAdapter(private var list:ArrayList<Items>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) view =
            LayoutInflater.from(parent?.context).inflate(R.layout.spinneritem, parent, false)
        view?.findViewById<TextView>(R.id.Item)?.text = list[position].item
        return view!!
    }
}