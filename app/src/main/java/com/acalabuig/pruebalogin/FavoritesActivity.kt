package com.acalabuig.pruebalogin
import android.content.Intent
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

    private  var usuario: UserEntity? = null
    private lateinit var db: UserDatabase
    private lateinit var noticiasAdapter: NoticiasAdapter
    private val currentUserId: Int = 1

    private lateinit var binding: ActivityFavoritesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        usuario = intent.getSerializableExtra("Usuario") as? UserEntity


        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noticiaObtenida = intent.getSerializableExtra("Noticia") as NoticiaEntity


        val titulo: String = noticiaObtenida.title
        val descripcion: String = noticiaObtenida.content
        val fecha: String = noticiaObtenida.fecha

        val et_titulo = binding.insertarTitulo
        val et_descripcion = binding.insertarResumen
        val et_fecha = binding.insertarFecha
        val btn_actualizar = binding.btnGuardar

        et_titulo.setText(titulo)
        et_descripcion.setText(descripcion)
        et_fecha.setText(fecha)

        btn_actualizar.setOnClickListener {
            val id = noticiaObtenida.id
            val esFavorita = noticiaObtenida.esFavorita
            val imagenUrl = noticiaObtenida.imageurl
            val noticiaUrl = noticiaObtenida.noticiaurl
            val noticiaActualizada = NoticiaEntity(
                id = id,
                title = et_titulo.text.toString(),
                content = et_descripcion.text.toString(),
                fecha = et_fecha.text.toString(),
                esFavorita = esFavorita,
                imageurl = imagenUrl,
                noticiaurl = noticiaUrl)
            loadFavorites(noticiaActualizada)
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("Usuario", usuario)
            startActivity(intent)
        }

    }

    private fun loadFavorites(noticiaActualizada: NoticiaEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
          UserApplication
            database.noticiaDao().update(noticiaActualizada)

        }
    }
}