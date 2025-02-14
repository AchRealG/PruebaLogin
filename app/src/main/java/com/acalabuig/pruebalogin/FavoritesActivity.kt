package com.acalabuig.pruebalogin
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.acalabuig.pruebalogin.UserApplication.Companion.database
import com.acalabuig.pruebalogin.databinding.ActivityFavoritesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoritesActivity : AppCompatActivity() {
    private lateinit var db: UserDatabase
    private lateinit var noticiasAdapter: NoticiasAdapter
    private val currentUserId: Int = 1

    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

       // db = UserDatabase.getInstance(this)
        db = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "UserDatabase").build()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(this)

        noticiasAdapter = NoticiasAdapter(this, db)
        recyclerView.adapter = noticiasAdapter

        val noticiaActualizada = NoticiaEntity(
            id = id,
            title = et_titulo.text.toString(),
            content = et_descripcion.text.toString(),
            fecha = et_fecha.text.toString(),
            esFavorita = esFavorita,
            imageurl = imagenUrl,
            noticiaurl = noticiaUrl)

        loadFavorites(noticiaActualizada)
    }

    private fun loadFavorites(noticiaActualizada: NoticiaEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
          UserApplication
            database.noticiaDao().update(noticiaActualizada)

        }
    }
}