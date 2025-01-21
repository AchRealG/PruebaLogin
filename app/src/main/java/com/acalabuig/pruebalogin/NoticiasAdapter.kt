package com.acalabuig.pruebalogin

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoticiasAdapter : RecyclerView.Adapter<NoticiasAdapter.NoticiaViewHolder>() {
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
        holder.urlTextView.text = noticia.url

        holder.itemView.setOnClickListener {
            // Handle onClick for editing the noticia
            val intent = Intent(holder.itemView.context, EditNoticiaActivity::class.java)
            intent.putExtra("noticiaId", noticia.id)
            holder.itemView.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                db.noticiaDao().delete(noticia)
                lifecycleScope.launch(Dispatchers.IO) {
                    loadNoticias()
                }
            }
            true
        }
    }

    override fun getItemCount(): Int {
        return noticias.size
    }

    class NoticiaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.title)
        val contentTextView: TextView = itemView.findViewById(R.id.content)
        val urlTextView: TextView = itemView.findViewById(R.id.url)
    }
}