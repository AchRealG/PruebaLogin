package com.acalabuig.pruebalogin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast


import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.acalabuig.pruebalogin.databinding.ItemNoticiaBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoticiasAdapter (private var newslist: MutableList<NoticiaEntity>,
                       private val listener: OnClickListener) : RecyclerView.Adapter<NoticiasAdapter.ViewHolder>() {

    private lateinit var context: Context



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding = ItemNoticiaBinding.bind(view)

        fun establecerListener(noticia: NoticiaEntity) {
            with(binding) {
                root.setOnClickListener { listener.OnClick(noticia) }

                btnLike.setOnClickListener { listener.OnClickFavorite(noticia) }

                root.setOnLongClickListener {
                    listener.OnDelete(noticia)
                    true
                }
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_noticia, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = newslist.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val noticia = newslist[position]

        with(holder) {
            establecerListener(noticia)

            with(binding) {
                tvTitle.text = noticia.title
                tvDescription.text = noticia.content
                tvDate.text = noticia.fecha

                val icono = if (noticia.esFavorita) R.drawable.ic_like else R.drawable.ic_unlike
                btnLike.setImageResource(icono)

                btnOpenLink.setOnClickListener {
                    val url = noticia.noticiaurl
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Error Request", Toast.LENGTH_SHORT).show()
                    }
                }

                Glide.with(binding.tvImage.context)
                    .load(noticia.imageurl)
                    .error(R.drawable.ic_launcher_background)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(binding.tvImage)
            }
        }
    }

    fun agregar(noticia: NoticiaEntity) {
        newslist.add(noticia)
        notifyDataSetChanged()
    }

    fun establecerNoticias(noticias: MutableList<NoticiaEntity>) {
        this.newslist = noticias
        notifyDataSetChanged()
    }

    fun actualizar(noticia: NoticiaEntity) {
        val indice =  newslist.indexOfFirst { it.id == noticia.id }

        if (indice != -1) {
            newslist[indice] = noticia
            notifyItemChanged(indice)
        }
    }

    fun eliminar(noticia: NoticiaEntity) {
        val indice =  newslist.indexOf(noticia)

        if (indice != -1) {
            newslist.removeAt(indice)
            notifyItemRemoved(indice)
        }
    }




}