package com.example.firebaseacticidad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Declaramos lo siguiente para usar FirebaseDatabase
    private lateinit var dbReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*Cuando precionamos el button de registar lo primero que hace es ver que no vayan campos basios
        Por lo cual los datos son registrados completos, se agrego en el codigo XML que en el editText
        solo se puedan introducir numeros en los que se requiere numeros.

        Si el button detecta espacios vacios dira el mensaje side peticion de llenado de dato, si los editText
        estan llenos mandara los datos de los editText a la funcion que Guardar para registrar los datos y sacar el
        promedio y guarda ademas de los datos registrados, guarda el promedio.*/
        btRegister.setOnClickListener{
            if (edTextNombre.text.isEmpty()||edTextLabo1.text.isEmpty()||edTextLabo2.text.isEmpty()
                ||edTextParcial.text.isEmpty()){
                Toast.makeText(this, " Favor rellenar todos los campos", Toast.LENGTH_SHORT).show()
            }
            else{
            Guardar(nombre = edTextNombre.text.toString(), lab2 = edTextLabo2.text.toString().toDouble(), parcial = edTextParcial.text.toString().toDouble(), lab1 = edTextLabo1.text.toString().toDouble())
            }
        }

        // Aqui lo que hacemos unicamente es cambiar de activity y los lleva a una activity donde mostara los datos
        btViewRegister.setOnClickListener{
            val abrir =Intent(this, VerRegistros::class.java)
            startActivity(abrir)
        }
    }

    //Donde creamos el menu de opciones que los dara la opcion de ver mas informacion del desarrollador
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Funcion que guarda los datos en Firebase, saca el promedio y lo guarda
    private fun Guardar(nombre: String, lab1: Double, lab2: Double, parcial: Double) {
            val a = lab1 * 0.2
            val b = lab2 * 0.2
            val c = parcial * 0.4
            val total = a + b + c

            val student = HashMap<String, String>()
            student["Nombre"] = nombre
            student["Laboratorio 1 | 20% : "] = lab1.toString()
            student["Laboratorio 2 | 20% : "] = lab2.toString()
            student["Parcial | 40% : "] = parcial.toString()
            student["Promedio"] = total.toString()
            val rootRef = FirebaseDatabase.getInstance().reference
            val tasksRef = rootRef.child("Estudiantes").push()
            tasksRef.setValue(student)
            Toast.makeText(this, "Registro de datos exitoso", Toast.LENGTH_LONG).show()
            edTextNombre.setText("")
            edTextLabo1.setText("")
            edTextLabo2.setText("")
            edTextParcial.setText("")
    }


    //Aqui los cabiamos a la actividy Informacion
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.info-> {
                val intent = Intent(this, Information::class.java).apply {
                }
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
        return super.onOptionsItemSelected(item)
    }
}

