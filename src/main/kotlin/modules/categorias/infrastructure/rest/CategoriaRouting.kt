package com.finanzasana.modules.categorias.infrastructure.rest

import com.finanzasana.modules.categorias.domain.repository.CategoriaRepository
import com.finanzasana.modules.categorias.infrastructure.rest.dto.toResponse
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.categoriaRouting() {

    val categoriaRepository by inject<CategoriaRepository>()

    get("/categorias") {
        val categorias = categoriaRepository.listar()
        call.respond(categorias.map { it.toResponse() })
    }

}

