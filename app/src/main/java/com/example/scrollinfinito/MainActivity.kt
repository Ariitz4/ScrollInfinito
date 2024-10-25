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


    private lateinit var checkBoxLimpieza: CheckBox
    private lateinit var checkBoxCompra: CheckBox
    private lateinit var checkBoxRecordatorio: CheckBox
    private lateinit var checkBoxReuniones: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tareas = mutableListOf()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTareas)
        val editTextTarea = findViewById<EditText>(R.id.editTextTarea)
        val buttonAñadir = findViewById<Button>(R.id.buttonAñadir)
        val spinnerCategorias = findViewById<Spinner>(R.id.spinnerCategorias)

        val categorias = arrayOf("Limpieza", "Compra", "Recordatorio", "Reuniones", "Deberes")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategorias.adapter = adapter


        recyclerView.layoutManager = LinearLayoutManager(this)
        tareaAdapter = TareaAdapter(tareas)
        recyclerView.adapter = tareaAdapter

        checkBoxLimpieza = findViewById(R.id.checkBoxLimpieza)
        checkBoxCompra = findViewById(R.id.checkBoxCompra)
        checkBoxRecordatorio = findViewById(R.id.checkBoxRecordatorio)
        checkBoxReuniones = findViewById(R.id.checkBoxReuniones)

        buttonAñadir.setOnClickListener {
            val nuevaTareaDescripcion = editTextTarea.text.toString().trim()
            val categoriaSeleccionada = spinnerCategorias.selectedItem.toString()

            if (nuevaTareaDescripcion.isNotEmpty()) {
                tareaAdapter.agregarTarea("$nuevaTareaDescripcion - $categoriaSeleccionada")
                editTextTarea.text.clear()
                recyclerView.scrollToPosition(0)
            }
        }

        checkBoxLimpieza.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) deselectOtherCheckboxes(checkBoxLimpieza)
        }
        checkBoxCompra.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) deselectOtherCheckboxes(checkBoxCompra)
        }
        checkBoxRecordatorio.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) deselectOtherCheckboxes(checkBoxRecordatorio)
        }
        checkBoxReuniones.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) deselectOtherCheckboxes(checkBoxReuniones)
        }
    }

    private fun deselectOtherCheckboxes(selectedCheckBox: CheckBox) {
        listOf(checkBoxLimpieza, checkBoxCompra, checkBoxRecordatorio, checkBoxReuniones).forEach {
            if (it != selectedCheckBox) {
                it.isChecked = false
            }
        }
    }
}
