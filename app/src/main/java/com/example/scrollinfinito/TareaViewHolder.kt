package com.example.scrollinfinito
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TareaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.textViewTarea)
    val imageViewEliminar: ImageView = itemView.findViewById(R.id.imageViewEliminar)
}