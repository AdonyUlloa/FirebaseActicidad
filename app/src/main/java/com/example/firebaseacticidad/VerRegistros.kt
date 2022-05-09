package com.example.firebaseacticidad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_ver_registros.*

//Declaramos lo siguiente para usar FirebaseDatabase
private lateinit var dbReference: DatabaseReference
private lateinit var firebaseDatabase: FirebaseDatabase

class VerRegistros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_registros)

        // Button que recoge el dato del editText y manda a la funcion que buscara el dato en Firebase
        btVerRegistro.setOnClickListener{
            leer(nombre = registroABuscar.text.toString())
        }
    }

    //Funcion que buscara el dato y lo hara aparecer el en TextView dato1
    fun leer(nombre: String) {

        val myRef = FirebaseDatabase.getInstance().reference
        val query: Query = myRef.child("Estudiantes").orderByChild("Nombre").equalTo(nombre)
        with(query) {
            addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (citi in dataSnapshot.children) {
                            dato1.setText(citi.value.toString())
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
}