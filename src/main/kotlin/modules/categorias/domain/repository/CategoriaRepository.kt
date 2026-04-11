package com.finanzasana.modules.categorias.domain.repository

import com.finanzasana.modules.categorias.domain.model.Categoria

interface CategoriaRepository {
    suspend fun listar(): List<Categoria>
    suspend fun obtenerNombrePorId(id: Int): String
}
