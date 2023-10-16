package com.example.sliit_news

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.sliit_news.R

class NewsViewHolder(view: View) : ViewHolder(view) {

    val cbNews: CheckBox = view.findViewById(R.id.cbNews)
    val ivDelete: ImageView = view.findViewById(R.id.ivDelete)
    val descriptionTextView: TextView = view.findViewById(R.id.descriptionTextView) // Add this line
    val dateTextView: TextView = view.findViewById(R.id.dateTextView) // Add this line
}
