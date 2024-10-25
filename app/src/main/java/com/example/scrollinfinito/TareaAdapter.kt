package com.example.scrollinfinito
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TareaAdapter(private val tareas: MutableList<String>) :
    RecyclerView.Adapter<TareaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea, parent, false)
        return TareaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        holder.textView.text = tareas[position]


        holder.imageViewEliminar.setOnClickListener {
            eliminarTarea(position)
        }
    }

    override fun getItemCount(): Int = tareas.size

    fun agregarTarea(tarea: String) {
        if (!tareas.contains(tarea)) {
            tareas.add(0, tarea)
            notifyItemInserted(0)
        }
    }


    fun eliminarTarea(position: Int) {
        if (position >= 0 && position < tareas.size) {
            tareas.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, tareas.size)
        }
    }
}