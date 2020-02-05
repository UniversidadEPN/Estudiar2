package com.epn.estudiar2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*




class MainActivity : AppCompatActivity() {

    internal lateinit var db: FirebaseFirestore
    internal lateinit var user: String
    internal lateinit var pass: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        cargarImagenUrl()

        db = FirebaseFirestore.getInstance()





    }

 //   -----login usuario y contraseña-------
    fun onclickIngresar(view: View){
        user= editTextUsuario.text.toString()
        pass= editText2Password.text.toString()

        db.collection("users").whereEqualTo("usuario",user).whereEqualTo("password", pass).get().addOnSuccessListener {
                queryDocumentSnapshots ->
            if(editTextUsuario.text.isEmpty() ){
                editTextUsuario.error="Ingrese un usuario"
            }


            if(editText2Password.text.isEmpty()){
                editText2Password.error="Ingrese una contraseña"
            }

            if(queryDocumentSnapshots.size()>0){

                var irLista = Intent(this, Lista::class.java)
                irLista.putExtra("usuario", editTextUsuario.text.toString())
                startActivity(irLista)
                finish()//cierra el activity
            }else{
                editTextUsuario.setText("")
                editText2Password.setText("")
                /*
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Advertencia")
                builder.setMessage("Usuario o cantraseña incorrectos")
                builder.setPositiveButton("Salir", { dialogInterface: DialogInterface, i: Int ->
                    finish()
                })
                builder.setNegativeButton("Salissr", { dialogInterface: DialogInterface, i: Int ->
                    builder.show()
                })*/

            }


        }

    }

    private fun cargarImagenUrl() {
        val imagenUrl= "https://i.pinimg.com/474x/90/f2/0e/90f20eff95f277c0d1152cecd1d07cc3.jpg"

        Glide.with(this).load(imagenUrl).apply(RequestOptions().placeholder(R.mipmap.ic_launcher)).into(imageView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
