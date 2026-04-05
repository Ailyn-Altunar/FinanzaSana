package com.finanzasana.modules.catalogoRol.infrastructure.rest

import com.finanzasana.modules.catalogoRol.application.usecase.ListarRolesUseCase
import com.finanzasana.modules.catalogoRol.application.usecase.VerRolUseCase
import com.finanzasana.modules.catalogoRol.infrastructure.rest.dto.RolResponse
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.rolRouting() {

    // Inyección de dependencias con Koin
    val listarRoles: ListarRolesUseCase by inject()
    val verRol: VerRolUseCase by inject()

    route("/roles") {

        get {
            val roles = listarRoles().map { RolResponse(it.id, it.nombre) }
            call.respond(roles)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond("ID inválido")
                return@get
            }

            val rol = verRol(id)
            if (rol == null) {
                call.respond("Rol no encontrado")
            } else {
                call.respond(RolResponse(rol.id, rol.nombre))
            }
        }
    }
}