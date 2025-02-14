package com.acalabuig.pruebalogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.acalabuig.pruebalogin.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() , OnClickListener{
    private lateinit var binding: ActivityMainBinding

    private lateinit var layoutLineal: LinearLayoutManager
    private lateinit var adaptadorNoticias: NoticiasAdapter

    private  var usuario: UserEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        usuario = intent.getSerializableExtra("Usuario") as? UserEntity

        val botonFlotante = binding.fab

        botonFlotante.setOnClickListener{
            Toast.makeText(this, "Creando noticia", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, NewNoticiaActivity::class.java)
            intent.putExtra("Usuario", usuario)
            startActivity(intent)
        }

        adaptadorNoticias = NoticiasAdapter(mutableListOf(), this)
        layoutLineal = LinearLayoutManager(this)

        getNews()

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = layoutLineal
            adapter = adaptadorNoticias
        }
    }

    private fun getNews() {
        lifecycleScope.launch(Dispatchers.IO) {
            val noticias =  UserApplication
                .database
                .noticiaDao()
                .getAllNoticias()

            val favoritos = usuario?.let {
                UserApplication
                    .database
                    .likesDao()
                    .getLikedNoticias(it.id)
            }

            noticias.forEach { noticia ->
                if (favoritos != null) {
                    noticia.esFavorita = favoritos.any { it.noticiaId.toInt() == noticia.id }
                }
            }

            withContext(Dispatchers.Main) {
                adaptadorNoticias.establecerNoticias(noticias)
            }
        }
    }

}