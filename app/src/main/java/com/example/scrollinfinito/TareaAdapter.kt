package com.example.scrollinfinito
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TareaAdapter(private val tareas: MutableList<String>,
                   private val tareasLimpieza: MutableList<String>,
                   private val tareasCompra: MutableList<String>,
                   private val tareasRecordatorio: MutableList<String>,
                   private val tareasReuniones: MutableList<String>,
                   private val tareasDeberes: MutableList<String>) :
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

    fun actualizarTareas(nuevasTareas: List<String>) {
        tareas.clear()
        tareas.addAll(nuevasTareas)
        notifyDataSetChanged()
    }

    fun eliminarTarea(position: Int) {
        if (position >= 0 && position < tareas.size) {
            val tareaEliminada = tareas[position]
            tareas.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, tareas.size)

            tareasLimpieza.remove(tareaEliminada)
            tareasCompra.remove(tareaEliminada)
            tareasRecordatorio.remove(tareaEliminada)
            tareasReuniones.remove(tareaEliminada)
            tareasDeberes.remove(tareaEliminada)
        }
    }
}