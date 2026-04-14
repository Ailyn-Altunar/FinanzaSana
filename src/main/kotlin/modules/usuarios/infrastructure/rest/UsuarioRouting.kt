package com.finanzasana.modules.usuarios.infrastructure.rest

import com.finanzasana.common.infrastructure.security.JwtConfig
import com.finanzasana.modules.usuarios.application.usecase.EliminarUsuarioUseCase
import com.finanzasana.modules.usuarios.application.usecase.LoginUseCase
import com.finanzasana.modules.usuarios.application.usecase.RegistrarUsuarioUseCase
import com.finanzasana.modules.usuarios.application.usecase.VerPerfilUseCase
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
    val verPerfilUseCase by inject<VerPerfilUseCase>()


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

            // PERFIL DEL USUARIO AUTENTICADO
            get("/perfil") {
                val principal = call.principal<JWTPrincipal>()!!
                val id = principal.payload.getClaim("id").asInt()

                val usuario = verPerfilUseCase.ejecutar(id)

                if (usuario == null) {
                    return@get call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to "Usuario no encontrado")
                    )
                }

                call.respond(HttpStatusCode.OK, usuario.toResponse())
            }

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

                val eliminado = eliminarUsuarioUseCase(id)

                if (eliminado) {
                    call.respond(
                        HttpStatusCode.OK,
                        mapOf("mensaje" to "Usuario eliminado correctamente")
                    )
                } else {
                    call.respond(
                        HttpStatusCode.NotFound,
                        mapOf("error" to "Usuario no encontrado")
                    )
                }
            }
        }
    }
}
