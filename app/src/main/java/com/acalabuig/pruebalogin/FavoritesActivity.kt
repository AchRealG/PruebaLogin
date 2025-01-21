package com.acalabuig.pruebalogin
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoritesActivity : AppCompatActivity() {
    private lateinit var db: UserDatabase
    private lateinit var noticiasAdapter: NoticiasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        db = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "app-database").build()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        noticiasAdapter = NoticiasAdapter()
        recyclerView.adapter = noticiasAdapter

        loadFavorites()
    }

    private fun loadFavorites() {
        lifecycleScope.launch(Dispatchers.IO) {
            val favorites = db.likesDao().getLikedNoticias(currentUserId)
            lifecycleScope.launch(Dispatchers.IO) {
                noticiasAdapter.setNoticias(favorites)
            }
        }
    }
}