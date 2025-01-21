package com.acalabuig.pruebalogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "app-database").build()

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
       var noticiasAdapter = NoticiasAdapter()
        recyclerView.adapter = noticiasAdapter

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this, NewNoticiaActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch(Dispatchers.IO) {

            val noticias = db.noticiaDao().getAllNoticias()
            lifecycleScope.launch(Dispatchers.IO) {
                noticiasAdapter.setNoticias(noticias)
            }
        }

        val favoritesButton = findViewById<Button>(R.id.favorites_button)
        favoritesButton.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
    }





}