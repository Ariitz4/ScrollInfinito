package com.example.scrollinfinito

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// Clase que adapta las tareas para el RecyclerView
class TareaAdapter(
    private val tareas: MutableList<String>,
    private val tareasLimpieza: MutableList<String>,
    private val tareasCompra: MutableList<String>,
    private val tareasRecordatorio: MutableList<String>,
    private val tareasReuniones: MutableList<String>,
    private val tareasDeberes: MutableList<String>
) : RecyclerView.Adapter<TareaViewHolder>() {

    // Método para crear una nueva vista y un ViewHolder correspondiente
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        // Infla el layout para cada elemento de la tarea
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea, parent, false)
        return TareaViewHolder(view) // Retorna el ViewHolder creado
    }

    // Método para enlazar datos con el ViewHolder
    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        // Establece el texto del TextView del ViewHolder con la tarea correspondiente
        holder.textView.text = tareas[position]

        // Configura el listener para el botón de eliminar tarea
        holder.imageViewEliminar.setOnClickListener {
            eliminarTarea(position) // Llama al método para eliminar la tarea
        }
    }

    // Retorna la cantidad total de tareas
    override fun getItemCount(): Int = tareas.size

    // Método para agregar una nueva tarea
    fun agregarTarea(tarea: String) {
        // Verifica que la tarea no esté ya en la lista
        if (!tareas.contains(tarea)) {
            tareas.add(0, tarea) // Agrega la tarea al inicio de la lista
            notifyItemInserted(0) // Notifica al RecyclerView que se insertó un nuevo item
        }
    }

    // Método para actualizar la lista de tareas
    fun actualizarTareas(nuevasTareas: List<String>) {
        tareas.clear() // Limpia la lista actual de tareas
        tareas.addAll(nuevasTareas) // Agrega las nuevas tareas
        notifyDataSetChanged() // Notifica al RecyclerView que se actualizó la lista
    }

    // Método para eliminar una tarea por su posición
    fun eliminarTarea(position: Int) {
        // Verifica que la posición sea válida
        if (position >= 0 && position < tareas.size) {
            val tareaEliminada = tareas[position] // Obtiene la tarea a eliminar
            tareas.removeAt(position) // Elimina la tarea de la lista
            notifyItemRemoved(position) // Notifica que se eliminó un item
            notifyItemRangeChanged(position, tareas.size) // Notifica cambios en el rango

            // Elimina la tarea de todas las listas de categorías
            tareasLimpieza.remove(tareaEliminada)
            tareasCompra.remove(tareaEliminada)
            tareasRecordatorio.remove(tareaEliminada)
            tareasReuniones.remove(tareaEliminada)
            tareasDeberes.remove(tareaEliminada)
        }
    }
}