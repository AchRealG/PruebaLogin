package com.acalabuig.pruebalogin
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NewNoticiaActivity : AppCompatActivity() {
    private lateinit var db: UserDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_noticia)

        db = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "app-database").build()

        val titleEditText = findViewById<EditText>(R.id.title)
        val contentEditText = findViewById<EditText>(R.id.content)
        val urlEditText = findViewById<EditText>(R.id.url)
        val saveButton = findViewById<Button>(R.id.save_button)

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()
            val url = urlEditText.text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                db.noticiaDao().insert(NoticiaEntity(title = title, content = content, url = url))
                lifecycleScope.launch(Dispatchers.IO) {
                    finish()
                }
            }
        }
    }
}