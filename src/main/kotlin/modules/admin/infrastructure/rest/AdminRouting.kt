package com.finanzasana.modules.admin.infrastructure.rest

import com.finanzasana.modules.admin.application.usecase.VerMetricasAdminUseCase
import com.finanzasana.modules.admin.application.usecase.VerActividadAdminUseCase
import com.finanzasana.modules.admin.application.usecase.VerUsuariosAdminUseCase
import com.finanzasana.modules.admin.infrastructure.rest.dto.toResponse
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.adminRouting() {

    val verMetricasAdminUseCase by inject<VerMetricasAdminUseCase>()
    val verActividadAdminUseCase by inject<VerActividadAdminUseCase>()
    val verUsuariosAdminUseCase by inject<VerUsuariosAdminUseCase>()

    authenticate("auth-jwt") {
        route("/admin") {

            // MÉTRICAS
            get("/metrics") {
                val principal = call.principal<JWTPrincipal>()!!
                val rol = principal.payload.getClaim("idRol").asInt()

                if (rol != 1) {
                    return@get call.respond(
                        HttpStatusCode.Forbidden,
                        mapOf("error" to "No tienes permisos para ver métricas de administrador")
                    )
                }

                val metricas = verMetricasAdminUseCase.ejecutar()
                call.respond(HttpStatusCode.OK, metricas.toResponse())
            }

            get("/actividad") {
                val principal = call.principal<JWTPrincipal>()!!
                val rol = principal.payload.getClaim("idRol").asInt()

                if (rol != 1) {
                    return@get call.respond(
                        HttpStatusCode.Forbidden,
                        mapOf("error" to "No tienes permisos para ver la actividad del sistema")
                    )
                }

                val actividad = verActividadAdminUseCase.ejecutar()
                call.respond(HttpStatusCode.OK, actividad.toResponse())
            }

            get("/usuarios") {
                val principal = call.principal<JWTPrincipal>()!!
                val rol = principal.payload.getClaim("idRol").asInt()

                if (rol != 1) {
                    return@get call.respond(
                        HttpStatusCode.Forbidden,
                        mapOf("error" to "No tienes permisos para ver la lista de usuarios")
                    )
                }

                val usuarios = verUsuariosAdminUseCase()
                call.respond(HttpStatusCode.OK, usuarios.toResponse())
            }
        }
    }
}
