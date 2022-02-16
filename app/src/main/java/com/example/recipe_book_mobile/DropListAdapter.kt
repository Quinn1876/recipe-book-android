package com.example.recipe_book_mobile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.drop_list_item.view.*

class DropListAdapter(
    val data: ArrayList<String>,
    val orderedList: Boolean
): RecyclerView.Adapter<DropListAdapter.DropListViewHolder>() {
    class DropListViewHolder(val listItem: LinearLayout): RecyclerView.ViewHolder(listItem)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DropListViewHolder{
        val listItemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.drop_list_item,
                parent,
                false
            ) as LinearLayout
        // Set the size of the view's size, margnis, padding, and layout paramaters here
        return DropListViewHolder(listItemView)
    }

    override fun onBindViewHolder(holder: DropListAdapter.DropListViewHolder, position: Int) {
        holder.listItem.text_view_drop_list_item.text = data[position]
    }

    override fun getItemCount(): Int = data.size
}