package com.acalabuig.pruebalogin

interface OnClickListener {
    fun alHacerClic(noticiaEntity: NoticiaEntity)

    fun alDarleAFavorito(noticiaEntity: NoticiaEntity)

    fun alEliminar(noticiaEntity: NoticiaEntity)
}