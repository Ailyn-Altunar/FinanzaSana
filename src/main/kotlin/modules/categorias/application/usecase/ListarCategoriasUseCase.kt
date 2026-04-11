package com.finanzasana.modules.categorias.application.usecase

import com.finanzasana.modules.categorias.domain.model.Categoria
import com.finanzasana.modules.categorias.domain.repository.CategoriaRepository

class ListarCategoriasUseCase(
    private val categoriaRepository: CategoriaRepository
) {
    suspend fun ejecutar(): List<Categoria> {
        return categoriaRepository.listar()
    }
}
