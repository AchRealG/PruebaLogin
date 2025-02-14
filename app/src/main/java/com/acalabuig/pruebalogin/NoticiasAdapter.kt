package com.acalabuig.pruebalogin

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoticiasAdapter (private val lifecycleOwner: LifecycleOwner,
                       private val db: UserDatabase) : RecyclerView.Adapter<NoticiasAdapter.NoticiaViewHolder>() {
    private var noticias: List<NoticiaEntity> = listOf()

    fun setNoticias(noticias: List<NoticiaEntity>) {
        this.noticias = noticias
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_noticia, parent, false)
        return NoticiaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val noticia = noticias[position]
        holder.titleTextView.text = noticia.title
        holder.contentTextView.text = noticia.content
        holder.urlTextView.text = noticia.imageurl

        holder.itemView.setOnClickListener {
            // Handle onClick for editing the noticia
            val intent = Intent(holder.itemView.context, NewNoticiaActivity::class.java)
            intent.putExtra("noticiaId", noticia.noticiaId)
            holder.itemView.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                db.noticiaDao().delete(noticia)
                withContext(Dispatchers.Main) {
                    loadNoticias()
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return noticias.size
    }

    private fun loadNoticias() {
        lifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val updatedNoticias = db.noticiaDao().getAllNoticias() // Assuming you have this method
            withContext(Dispatchers.Main) {
                setNoticias(updatedNoticias)
            }
        }
    }

    class NoticiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val contentTextView: TextView = itemView.findViewById(R.id.content)
        val urlTextView: TextView = itemView.findViewById(R.id.url)
    }
}