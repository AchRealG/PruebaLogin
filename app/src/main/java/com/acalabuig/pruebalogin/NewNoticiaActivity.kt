package com.acalabuig.pruebalogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.acalabuig.pruebalogin.UserApplication.Companion.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewNoticiaActivity : AppCompatActivity() {
    private lateinit var db: UserDatabase
    private  var usuario: UserEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_noticia)
        usuario = intent.getSerializableExtra("Usuario") as? UserEntity
        db = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "app-database").build()

        val titleEditText = findViewById<EditText>(R.id.title)
        val contentEditText = findViewById<EditText>(R.id.content)
        val fechaEditText = findViewById<EditText>(R.id.fecha)
        val imageUrlEditText = findViewById<EditText>(R.id.imageurl)
        val noticiaUrlEditText = findViewById<EditText>(R.id.noticiaurl)
        val saveButton = findViewById<Button>(R.id.save_button)

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()
            val fecha = fechaEditText.text.toString()
            val imageUrl = imageUrlEditText.text.toString()
            val noticiaUrl = noticiaUrlEditText.text.toString()

            if (title.isNotEmpty() && content.isNotEmpty() && fecha.isNotEmpty() && imageUrl.isNotEmpty() && noticiaUrl.isNotEmpty()) {

                    val noticia = NoticiaEntity(
                        title = title,
                        content = content,
                        fecha = fecha,
                        imageurl = imageUrl,
                        noticiaurl = noticiaUrl
                    )
                   saveNews(noticia)
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("Usuario", usuario)
                    startActivity(intent)
                    Toast.makeText(this@NewNoticiaActivity, "Se ha guardado correctamente", Toast.LENGTH_SHORT).show()



            } else {
                // Show a message to the user indicating that all fields are required
                runOnUiThread  {
                    Toast.makeText(this@NewNoticiaActivity, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                }
            }
        }


    }

    private fun saveNews(noticiaActualizada: NoticiaEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            UserApplication
            database.noticiaDao().insert(noticiaActualizada)

        }
    }
}