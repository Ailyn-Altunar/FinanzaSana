package com.finanzasana.modules.solicitudes.infrastructure.rest

import com.finanzasana.modules.solicitudes.application.usecase.CrearSolicitudUseCase
import com.finanzasana.modules.solicitudes.application.usecase.ListarHistorialSolicitudesUseCase
import com.finanzasana.modules.solicitudes.application.usecase.ListarSolicitudesPendientesAdminUseCase
import com.finanzasana.modules.solicitudes.application.usecase.AprobarSolicitudUseCase
import com.finanzasana.modules.solicitudes.application.usecase.RechazarSolicitudUseCase
import com.finanzasana.modules.solicitudes.infrastructure.rest.dto.*
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.solicitudPrestamoRouting() {

    val crearSolicitudUseCase by inject<CrearSolicitudUseCase>()
    val listarSolicitudesPendientesAdminUseCase by inject<ListarSolicitudesPendientesAdminUseCase>()
    val listarHistorialSolicitudesUseCase by inject<ListarHistorialSolicitudesUseCase>()
    val aprobarSolicitudUseCase by inject<AprobarSolicitudUseCase>()
    val rechazarSolicitudUseCase by inject<RechazarSolicitudUseCase>()

    route("/solicitudes") {

        // CREAR SOLICITUD (NO requiere autenticación)
        post {
            val request = call.receive<SolicitudPrestamoRequest>()

            val nueva = crearSolicitudUseCase.ejecutar(
                idUsuario = request.idUsuario,
                idEmpresa = request.idEmpresa,
                montoSolicitado = request.montoSolicitado,
                meses = request.meses,
                motivo = request.motivo,
                idCategoria = request.idCategoria,
                imagenBase64 = request.imagenBase64,
                latitud = request.latitud,
                longitud = request.longitud
            )

            call.respond(HttpStatusCode.Created, nueva.toResponse())
        }

        // RUTAS QUE SOLO EL ADMIN PUEDE USAR
        authenticate("auth-jwt") {

            // LISTAR SOLICITUDES PENDIENTES
            get("/pendientes") {
                val principal = call.principal<JWTPrincipal>()!!
                val rol = principal.payload.getClaim("idRol").asInt()

                if (rol != 1) {
                    return@get call.respond(
                        HttpStatusCode.Forbidden,
                        mapOf("error" to "Solo un administrador puede ver solicitudes pendientes")
                    )
                }

                val solicitudes = listarSolicitudesPendientesAdminUseCase.ejecutar()
                call.respond(HttpStatusCode.OK, solicitudes.toPendientesAdminResponse())
            }

            get("/historial") {
                val principal = call.principal<JWTPrincipal>()!!
                val rol = principal.payload.getClaim("idRol").asInt()

                if (rol != 1) {
                    return@get call.respond(
                        HttpStatusCode.Forbidden,
                        mapOf("error" to "Solo un administrador puede ver el historial de solicitudes")
                    )
                }

                val historial = listarHistorialSolicitudesUseCase.ejecutar()
                call.respond(HttpStatusCode.OK, historial.toHistorialAdminResponse())
            }

            // APROBAR SOLICITUD
            post("/{id}/aprobar") {
                val principal = call.principal<JWTPrincipal>()!!
                val rol = principal.payload.getClaim("idRol").asInt()

                if (rol != 1) {
                    return@post call.respond(
                        HttpStatusCode.Forbidden,
                        mapOf("error" to "Solo un administrador puede aprobar solicitudes")
                    )
                }

                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    return@post call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "ID inválido")
                    )
                }

                aprobarSolicitudUseCase.ejecutar(id)

                call.respond(
                    HttpStatusCode.OK,
                    mapOf("mensaje" to "Solicitud aprobada correctamente")
                )
            }

            // RECHAZAR SOLICITUD
            post("/{id}/rechazar") {
                val principal = call.principal<JWTPrincipal>()!!
                val rol = principal.payload.getClaim("idRol").asInt()

                if (rol != 1) {
                    return@post call.respond(
                        HttpStatusCode.Forbidden,
                        mapOf("error" to "Solo un administrador puede rechazar solicitudes")
                    )
                }

                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    return@post call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "ID inválido")
                    )
                }

                rechazarSolicitudUseCase.ejecutar(id)

                call.respond(
                    HttpStatusCode.OK,
                    mapOf("mensaje" to "Solicitud rechazada correctamente")
                )
            }
        }
    }
}
