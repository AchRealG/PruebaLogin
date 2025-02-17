package com.acalabuig.pruebalogin

interface OnClickListener {
    fun OnClick(noticiaEntity: NoticiaEntity)

    fun OnClickFavorite(noticiaEntity: NoticiaEntity)

    fun OnDelete(noticiaEntity: NoticiaEntity)
}