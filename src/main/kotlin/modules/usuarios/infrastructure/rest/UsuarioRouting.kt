package com.finanzasana.modules.usuarios.infrastructure.rest

import com.finanzasana.common.infrastructure.security.JwtConfig
import com.finanzasana.modules.usuarios.application.usecase.EliminarUsuarioUseCase
import com.finanzasana.modules.usuarios.application.usecase.LoginUseCase
import com.finanzasana.modules.usuarios.application.usecase.RegistrarUsuarioUseCase
import com.finanzasana.modules.usuarios.infrastructure.rest.dto.*
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.usuarioRouting() {

    val loginUseCase by inject<LoginUseCase>()
    val registrarUsuarioUseCase by inject<RegistrarUsuarioUseCase>()


    route("/auth") {

        post("/login") {
            val request = call.receive<LoginRequest>()

            val usuario = loginUseCase.ejecutar(request.email, request.contrasena)

            if (usuario == null) {
                return@post call.respond(
                    HttpStatusCode.Unauthorized,
                    mapOf("error" to "Credenciales incorrectas")
                )
            }

            val token = JwtConfig.generateToken(usuario)

            call.respond(
                HttpStatusCode.OK,
                LoginResponse(
                    token = token,
                    usuario = usuario.toResponse()
                )
            )
        }
    }


    route("/usuarios") {

        post("/registro") {
            val request = call.receive<UsuarioRequest>()

            val nuevo = registrarUsuarioUseCase.ejecutar(
                nombre = request.nombre,
                email = request.email,
                contrasena = request.contrasena,
                idRol = request.idRol,
                telefono = request.telefono
            )

            if (nuevo == null) {
                return@post call.respond(
                    HttpStatusCode.BadRequest,
                    mapOf("error" to "No se pudo registrar el usuario")
                )
            }

            call.respond(HttpStatusCode.Created, nuevo.toResponse())
        }
    }


    authenticate("auth-jwt") {
        route("/usuarios") {

            // ELIMINAR USUARIO (SOLO ADMIN)
            delete("/{id}") {

                val eliminarUsuarioUseCase by inject<EliminarUsuarioUseCase>()

                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        mapOf("error" to "ID inválido")
                    )
                    return@delete
                }

                val principal = call.principal<JWTPrincipal>()!!
                val rol = principal.payload.getClaim("idRol").asInt()

                if (rol != 1) {
                    call.respond(
                        HttpStatusCode.Forbidden,
                        mapOf("error" to "Solo un administrador puede eliminar usuarios")
                    )
                    return@delete
                }

                eliminarUsuarioUseCase(id)

                call.respond(
                    HttpStatusCode.OK,
                    mapOf("mensaje" to "Usuario eliminado correctamente")
                )
            }
        }
    }
}
