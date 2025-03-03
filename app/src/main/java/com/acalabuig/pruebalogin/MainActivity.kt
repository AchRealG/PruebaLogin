package com.acalabuig.pruebalogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.acalabuig.pruebalogin.databinding.ActivityMainBinding
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

    override fun OnClick(noticiaEntity: NoticiaEntity) {
        val intent = Intent(this, FavoritesActivity::class.java)
        intent.putExtra("Noticia", noticiaEntity)
        intent.putExtra("Usuario", usuario)
        startActivity(intent)
    }

    override fun OnClickFavorite(noticiaEntity: NoticiaEntity) {
        noticiaEntity.esFavorita = !noticiaEntity.esFavorita
        adaptadorNoticias.actualizar(noticiaEntity)
        lifecycleScope.launch(Dispatchers.IO) {


            val favoritoEntity = LikesEntity(usuario?.id ?: -1, noticiaEntity.id)
            if (noticiaEntity.esFavorita) {
                UserApplication
                    .database
                    .likesDao()
                    .insert(favoritoEntity)
            } else {
                UserApplication
                    .database
                    .likesDao()
                    .delete(favoritoEntity)
            }
            UserApplication
                .database
                .noticiaDao()
                .update(noticiaEntity)
        }
    }

    override fun OnDelete(noticiaEntity: NoticiaEntity) {
        adaptadorNoticias.eliminar(noticiaEntity)
        lifecycleScope.launch(Dispatchers.IO) {
            UserApplication
                .database
                .noticiaDao()
                .delete(noticiaEntity)
        }
    }

}