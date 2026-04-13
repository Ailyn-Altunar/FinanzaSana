package com.finanzasana.modules.deudas.infrastructure.rest

import com.finanzasana.modules.deudas.application.usecase.*
import com.finanzasana.modules.deudas.infrastructure.rest.dto.*
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.abonoRouting() {

    val registrarAbonoUseCase by inject<RegistrarAbonoUseCase>()
    val listarAbonosPorDeudaUseCase by inject<ListarAbonosPorDeudaUseCase>()

    authenticate("auth-jwt") {
        route("/abonos") {


            get("/{idDeuda}") {
                val principal = call.principal<JWTPrincipal>()!!
                val idUsuario = principal.payload.getClaim("id").asInt()

                val idDeuda = call.parameters["idDeuda"]?.toIntOrNull()
                    ?: return@get call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "ID inválido")
                    )

                val abonos = listarAbonosPorDeudaUseCase.ejecutar(idDeuda, idUsuario)
                    .map { it.toResponse() }

                call.respond(HttpStatusCode.OK, abonos)
            }


            post("/{idDeuda}") {
                val principal = call.principal<JWTPrincipal>()!!
                val idUsuario = principal.payload.getClaim("id").asInt()

                val idDeuda = call.parameters["idDeuda"]?.toIntOrNull()
                    ?: return@post call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "ID inválido")
                    )

                val request = call.receive<AbonoRequest>()

                val abono = registrarAbonoUseCase.ejecutar(
                    idDeuda = idDeuda,
                    idUsuario = idUsuario,
                    monto = request.monto
                ) ?: return@post call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "No se pudo registrar el abono")
                )

                call.respond(HttpStatusCode.Created, abono.toResponse())
            }
        }
    }
}
