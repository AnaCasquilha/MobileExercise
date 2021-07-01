package com.example.mobileexercise.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileexercise.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photos.view.*

class ItemAdapter(private val items: ArrayList<String>):
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.photos, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = items[position]
        Picasso.get().load(photo).into(holder.ivPhotos)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivPhotos: ImageView = view.ivPhotos
    }

}