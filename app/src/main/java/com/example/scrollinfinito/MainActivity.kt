package com.example.scrollinfinito

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    // Adaptador para manejar la lista de tareas
    private lateinit var tareaAdapter: TareaAdapter
    // Lista principal para almacenar todas las tareas
    private lateinit var tareas: MutableList<String>
    // Listas para cada categoría de tareas
    private lateinit var tareasLimpieza: MutableList<String>
    private lateinit var tareasCompra: MutableList<String>
    private lateinit var tareasRecordatorio: MutableList<String>
    private lateinit var tareasReuniones: MutableList<String>
    private lateinit var tareasDeberes: MutableList<String>

    // Checkboxes para las categorías de tareas
    private lateinit var checkBoxLimpieza: CheckBox
    private lateinit var checkBoxCompra: CheckBox
    private lateinit var checkBoxRecordatorio: CheckBox
    private lateinit var checkBoxReuniones: CheckBox
    private lateinit var checkBoxDeberes: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Establece el layout de la actividad
        setContentView(R.layout.activity_main)

        // Inicializa las listas de tareas
        tareas = mutableListOf()
        tareasLimpieza = mutableListOf()
        tareasCompra = mutableListOf()
        tareasRecordatorio = mutableListOf()
        tareasReuniones = mutableListOf()
        tareasDeberes = mutableListOf()

        // Encuentra los elementos de la vista por su ID
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTareas)
        val editTextTarea = findViewById<EditText>(R.id.editTextTarea)
        val buttonAñadir = findViewById<Button>(R.id.buttonAñadir)
        val spinnerCategorias = findViewById<Spinner>(R.id.spinnerCategorias)

        // Inicializa los checkboxes
        checkBoxLimpieza = findViewById(R.id.checkBoxLimpieza)
        checkBoxCompra = findViewById(R.id.checkBoxCompra)
        checkBoxRecordatorio = findViewById(R.id.checkBoxRecordatorio)
        checkBoxReuniones = findViewById(R.id.checkBoxReuniones)
        checkBoxDeberes = findViewById(R.id.checkBoxDeberes)

        // Establece todos los checkboxes como seleccionados por defecto
        checkBoxLimpieza.isChecked = true
        checkBoxCompra.isChecked = true
        checkBoxRecordatorio.isChecked = true
        checkBoxReuniones.isChecked = true
        checkBoxDeberes.isChecked = true

        // Define las categorías disponibles en el spinner
        val categorias = arrayOf("Limpieza", "Compra", "Recordatorio", "Reuniones", "Deberes")
        // Crea un adaptador para el spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        // Establece el layout del dropdown del spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Asigna el adaptador al spinner
        spinnerCategorias.adapter = adapter

        // Configura el layout del RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        // Inicializa el adaptador de tareas y lo asigna al RecyclerView
        tareaAdapter = TareaAdapter(tareas, tareasLimpieza, tareasCompra, tareasRecordatorio, tareasReuniones, tareasDeberes)
        recyclerView.adapter = tareaAdapter

        // Configura el evento al hacer clic en el botón "Añadir"
        buttonAñadir.setOnClickListener {
            // Obtiene la descripción de la nueva tarea y la categoría seleccionada
            val nuevaTareaDescripcion = editTextTarea.text.toString().trim()
            val categoriaSeleccionada = spinnerCategorias.selectedItem.toString()

            // Verifica que la descripción no esté vacía
            if (nuevaTareaDescripcion.isNotEmpty()) {
                // Combina la descripción de la tarea con la categoría seleccionada
                val tareaConCategoria = "$nuevaTareaDescripcion - $categoriaSeleccionada"
                // Añade la tarea al adaptador
                tareaAdapter.agregarTarea(tareaConCategoria)
                // Limpia el campo de entrada
                editTextTarea.text.clear()
                // Desplaza el RecyclerView hacia la posición superior
                recyclerView.scrollToPosition(0)

                // Añade la tarea a la lista correspondiente según la categoría
                when (categoriaSeleccionada) {
                    "Limpieza" -> tareasLimpieza.add(tareaConCategoria)
                    "Compra" -> tareasCompra.add(tareaConCategoria)
                    "Recordatorio" -> tareasRecordatorio.add(tareaConCategoria)
                    "Reuniones" -> tareasReuniones.add(tareaConCategoria)
                    "Deberes" -> tareasDeberes.add(tareaConCategoria)
                }

                // Actualiza las tareas mostradas en el RecyclerView
                actualizarTareas()
            }
        }

        // Configura los eventos de cambio de estado de los checkboxes
        checkBoxLimpieza.setOnCheckedChangeListener { _, isChecked -> manejarCheckBox(checkBoxLimpieza, isChecked) }
        checkBoxCompra.setOnCheckedChangeListener { _, isChecked -> manejarCheckBox(checkBoxCompra, isChecked) }
        checkBoxRecordatorio.setOnCheckedChangeListener { _, isChecked -> manejarCheckBox(checkBoxRecordatorio, isChecked) }
        checkBoxReuniones.setOnCheckedChangeListener { _, isChecked -> manejarCheckBox(checkBoxReuniones, isChecked) }
        checkBoxDeberes.setOnCheckedChangeListener { _, isChecked -> manejarCheckBox(checkBoxDeberes, isChecked) }
    }

    // Método para manejar el estado de los checkboxes
    private fun manejarCheckBox(checkbox: CheckBox, isChecked: Boolean) {
        // Lista de todos los checkboxes
        val checkboxes = listOf(checkBoxLimpieza, checkBoxCompra, checkBoxRecordatorio, checkBoxReuniones, checkBoxDeberes)

        // Cuenta cuántos checkboxes están seleccionados
        val checkedCount = checkboxes.count { it.isChecked }

        // Si un checkbox se deselecciona y ninguno más está seleccionado
        if (!isChecked && checkedCount == 0) {
            // Vuelve a seleccionar el checkbox deseleccionado
            checkbox.isChecked = true
        } else {
            // Actualiza la lista de tareas mostradas
            actualizarTareas()
        }
    }

    // Método para actualizar la lista de tareas según los checkboxes seleccionados
    private fun actualizarTareas() {
        val tareasFiltradas = mutableListOf<String>()

        // Añade las tareas de las categorías seleccionadas a la lista filtrada
        if (checkBoxLimpieza.isChecked) {
            tareasFiltradas.addAll(tareasLimpieza)
        }
        if (checkBoxCompra.isChecked) {
            tareasFiltradas.addAll(tareasCompra)
        }
        if (checkBoxRecordatorio.isChecked) {
            tareasFiltradas.addAll(tareasRecordatorio)
        }
        if (checkBoxReuniones.isChecked) {
            tareasFiltradas.addAll(tareasReuniones)
        }
        if (checkBoxDeberes.isChecked) {
            tareasFiltradas.addAll(tareasDeberes)
        }

        // Si no hay tareas filtradas, muestra todas las tareas
        if (tareasFiltradas.isEmpty()) {
            tareasFiltradas.addAll(tareas)
        }

        // Actualiza el adaptador con la lista de tareas filtradas
        tareaAdapter.actualizarTareas(tareasFiltradas)
    }
}

