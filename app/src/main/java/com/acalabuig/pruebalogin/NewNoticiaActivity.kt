package com.acalabuig.pruebalogin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewNoticiaActivity : AppCompatActivity() {
    private lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_noticia)

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
                lifecycleScope.launch(Dispatchers.IO) {
                    val noticia = NoticiaEntity(
                        title = title,
                        content = content,
                        fecha = fecha,
                        imageurl = imageUrl,
                        noticiaurl = noticiaUrl
                    )
                    db.noticiaDao().insert(noticia)
                    withContext(Dispatchers.Main) {
                        finish()
                    }
                }
            } else {
                // Show a message to the user indicating that all fields are required
                runOnUiThread  {
                    Toast.makeText(this@NewNoticiaActivity, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}