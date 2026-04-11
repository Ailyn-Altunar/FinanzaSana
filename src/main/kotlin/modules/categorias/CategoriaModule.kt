package com.finanzasana.modules.categorias

import com.finanzasana.modules.categorias.application.usecase.ListarCategoriasUseCase
import com.finanzasana.modules.categorias.domain.repository.CategoriaRepository
import com.finanzasana.modules.categorias.infrastructure.persistence.PostgresCategoriaRepository
import org.koin.dsl.module

val categoriaModule = module {

    factory { ListarCategoriasUseCase(get()) }

    single<CategoriaRepository> { PostgresCategoriaRepository() }
}
