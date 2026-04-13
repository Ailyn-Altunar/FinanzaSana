package com.finanzasana.modules.planificador.infrastructure.rest

import com.finanzasana.modules.planificador.application.usecase.GenerarPlanificadorUseCase
import com.finanzasana.modules.planificador.infrastructure.rest.dto.PlanificadorResponse
import com.finanzasana.modules.deudas.infrastructure.rest.dto.DeudaResponse
import com.finanzasana.modules.deudas.infrastructure.rest.dto.toResponse
import com.finanzasana.modules.categorias.domain.repository.CategoriaRepository
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.http.*
import org.koin.ktor.ext.inject

fun Route.planificadorRouting() {

    val generarPlanificadorUseCase by inject<GenerarPlanificadorUseCase>()
    val categoriaRepository by inject<CategoriaRepository>()

    authenticate("auth-jwt") {
        get("/planificador/{metodo}") {

            val principal = call.principal<JWTPrincipal>()!!
            val idUsuario = principal.payload.getClaim("id").asInt()

            val metodo = call.parameters["metodo"] ?: "Bola de Nieve"

            val resultado = generarPlanificadorUseCase(idUsuario, metodo)

            // ============================
            //   CONVERTIR DEUDAS A DTO
            //   (MISMO ESTILO QUE deudaRouting)
            // ============================
            val deudasResponse = resultado.deudasOrdenadas.map { deuda ->
                val categoriaNombre = categoriaRepository.obtenerNombrePorId(deuda.idCategoria)
                deuda.toResponse(categoriaNombre, emptyList())
            }

            // ============================
            //   ARMAR RESPUESTA FINAL
            // ============================
            val response = PlanificadorResponse(
                metodo = resultado.metodo,
                totalDeuda = resultado.totalDeuda,
                tasaPromedio = resultado.tasaPromedio,
                deudasOrdenadas = deudasResponse
            )

            call.respond(HttpStatusCode.OK, response)
        }
    }
}
