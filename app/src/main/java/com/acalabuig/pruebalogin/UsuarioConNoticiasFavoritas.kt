package com.acalabuig.pruebalogin

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data  class UsuarioConNoticiasFavoritas (

    @Embedded
    val usuario: UserEntity,
    @Relation(
        parentColumn = "id", // Clave primaria de UsuarioEntity
        entityColumn = "id", // Clave primaria de NoticiaEntity
        associateBy = Junction(
            value = LikesEntity::class,
            parentColumn = "userId", // Campo en LikesEntity que referencia a UsuarioEntity
            entityColumn = "noticiaId"  // Campo en LikesEntity que referencia a NoticiaEntity
        )
    )
    val noticiasFavoritas: List<NoticiaEntity>
)