package com.finanzasana.modules.categorias.infrastructure.rest.dto

import com.finanzasana.modules.categorias.domain.model.Categoria
import kotlinx.serialization.Serializable

@Serializable
data class CategoriaResponse(
    val id: Int,
    val nombre: String
)

fun Categoria.toResponse() = CategoriaResponse(
    id = id ?: 0,
    nombre = nombre
)
