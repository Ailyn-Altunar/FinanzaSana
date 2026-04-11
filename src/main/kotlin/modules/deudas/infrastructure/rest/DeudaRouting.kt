package com.finanzasana.modules.deudas.infrastructure.rest

import com.finanzasana.modules.deudas.application.usecase.*
import com.finanzasana.modules.deudas.infrastructure.rest.dto.*
import com.finanzasana.modules.categorias.domain.repository.CategoriaRepository
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.deudaRouting() {

    val registrarDeudaUseCase by inject<RegistrarDeudaUseCase>()
    val listarDeudasUsuarioUseCase by inject<ListarDeudasUsuarioUseCase>()
    val verDetalleDeudaUseCase by inject<VerDetalleDeudaUseCase>()
    val listarAbonosPorDeudaUseCase by inject<ListarAbonosPorDeudaUseCase>()
    val categoriaRepository by inject<CategoriaRepository>()

    authenticate("auth-jwt") {
        route("/deudas") {

            // ============================
            // LISTAR DEUDAS DEL USUARIO
            // ============================
            get {
                val principal = call.principal<JWTPrincipal>()!!
                val idUsuario = principal.payload.getClaim("id").asInt()

                val deudas = listarDeudasUsuarioUseCase.ejecutar(idUsuario)

                val response = deudas.map { deuda ->
                    val categoriaNombre = categoriaRepository.obtenerNombrePorId(deuda.idCategoria)
                    deuda.toResponse(categoriaNombre, emptyList())
                }

                call.respond(HttpStatusCode.OK, response)
            }

            // ============================
            // CREAR DEUDA
            // ============================
            post {
                val principal = call.principal<JWTPrincipal>()!!
                val idUsuario = principal.payload.getClaim("id").asInt()

                val request = call.receive<DeudaRequest>()
                val deuda = registrarDeudaUseCase.ejecutar(request.toDomain(idUsuario))

                val categoriaNombre = categoriaRepository.obtenerNombrePorId(deuda.idCategoria)

                call.respond(HttpStatusCode.Created, deuda.toResponse(categoriaNombre, emptyList()))
            }

            // ============================
            // DETALLE DE UNA DEUDA
            // ============================
            get("/{idDeuda}") {
                val principal = call.principal<JWTPrincipal>()!!
                val idUsuario = principal.payload.getClaim("id").asInt()

                val idDeuda = call.parameters["idDeuda"]?.toIntOrNull()
                    ?: return@get call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "ID inválido")
                    )

                val deuda = verDetalleDeudaUseCase.ejecutar(idDeuda, idUsuario)
                    ?: return@get call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to "Deuda no encontrada")
                    )

                val abonos = listarAbonosPorDeudaUseCase.ejecutar(idDeuda, idUsuario)
                    .map { it.toResponse() }

                val categoriaNombre = categoriaRepository.obtenerNombrePorId(deuda.idCategoria)

                call.respond(HttpStatusCode.OK, deuda.toResponse(categoriaNombre, abonos))
            }
        }
    }
}
