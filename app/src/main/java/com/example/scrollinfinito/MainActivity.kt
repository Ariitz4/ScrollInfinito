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

    private lateinit var tareaAdapter: TareaAdapter
    private lateinit var tareas: MutableList<String>
    private lateinit var tareasLimpieza: MutableList<String>
    private lateinit var tareasCompra: MutableList<String>
    private lateinit var tareasRecordatorio: MutableList<String>
    private lateinit var tareasReuniones: MutableList<String>
    private lateinit var tareasDeberes: MutableList<String>

    private lateinit var checkBoxLimpieza: CheckBox
    private lateinit var checkBoxCompra: CheckBox
    private lateinit var checkBoxRecordatorio: CheckBox
    private lateinit var checkBoxReuniones: CheckBox
    private lateinit var checkBoxDeberes: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tareas = mutableListOf()
        tareasLimpieza = mutableListOf()
        tareasCompra = mutableListOf()
        tareasRecordatorio = mutableListOf()
        tareasReuniones = mutableListOf()
        tareasDeberes = mutableListOf()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTareas)
        val editTextTarea = findViewById<EditText>(R.id.editTextTarea)
        val buttonAñadir = findViewById<Button>(R.id.buttonAñadir)
        val spinnerCategorias = findViewById<Spinner>(R.id.spinnerCategorias)

        checkBoxLimpieza = findViewById(R.id.checkBoxLimpieza)
        checkBoxCompra = findViewById(R.id.checkBoxCompra)
        checkBoxRecordatorio = findViewById(R.id.checkBoxRecordatorio)
        checkBoxReuniones = findViewById(R.id.checkBoxReuniones)
        checkBoxDeberes = findViewById(R.id.checkBoxDeberes)


        checkBoxLimpieza.isChecked = true
        checkBoxCompra.isChecked = true
        checkBoxRecordatorio.isChecked = true
        checkBoxReuniones.isChecked = true
        checkBoxDeberes.isChecked = true

        val categorias = arrayOf("Limpieza", "Compra", "Recordatorio", "Reuniones", "Deberes")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategorias.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(this)
        tareaAdapter = TareaAdapter(tareas, tareasLimpieza, tareasCompra, tareasRecordatorio, tareasReuniones, tareasDeberes)
        recyclerView.adapter = tareaAdapter

        buttonAñadir.setOnClickListener {
            val nuevaTareaDescripcion = editTextTarea.text.toString().trim()
            val categoriaSeleccionada = spinnerCategorias.selectedItem.toString()

            if (nuevaTareaDescripcion.isNotEmpty()) {
                val tareaConCategoria = "$nuevaTareaDescripcion - $categoriaSeleccionada"
                tareaAdapter.agregarTarea(tareaConCategoria)
                editTextTarea.text.clear()
                recyclerView.scrollToPosition(0)


                when (categoriaSeleccionada) {
                    "Limpieza" -> tareasLimpieza.add(tareaConCategoria)
                    "Compra" -> tareasCompra.add(tareaConCategoria)
                    "Recordatorio" -> tareasRecordatorio.add(tareaConCategoria)
                    "Reuniones" -> tareasReuniones.add(tareaConCategoria)
                    "Deberes" -> tareasDeberes.add(tareaConCategoria)
                }

                actualizarTareas()
            }
        }


        checkBoxLimpieza.setOnCheckedChangeListener { _, isChecked -> manejarCheckBox(checkBoxLimpieza, isChecked) }
        checkBoxCompra.setOnCheckedChangeListener { _, isChecked -> manejarCheckBox(checkBoxCompra, isChecked) }
        checkBoxRecordatorio.setOnCheckedChangeListener { _, isChecked -> manejarCheckBox(checkBoxRecordatorio, isChecked) }
        checkBoxReuniones.setOnCheckedChangeListener { _, isChecked -> manejarCheckBox(checkBoxReuniones, isChecked) }
        checkBoxDeberes.setOnCheckedChangeListener { _, isChecked -> manejarCheckBox(checkBoxDeberes, isChecked) }
    }

    private fun manejarCheckBox(checkbox: CheckBox, isChecked: Boolean) {
        val checkboxes = listOf(checkBoxLimpieza, checkBoxCompra, checkBoxRecordatorio, checkBoxReuniones, checkBoxDeberes)


        val checkedCount = checkboxes.count { it.isChecked }


        if (!isChecked && checkedCount == 0) {

            checkbox.isChecked = true
        } else {

            actualizarTareas()
        }
    }

    private fun actualizarTareas() {
        val tareasFiltradas = mutableListOf<String>()

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


        if (tareasFiltradas.isEmpty()) {
            tareasFiltradas.addAll(tareas)
        }

        tareaAdapter.actualizarTareas(tareasFiltradas)
    }
}

