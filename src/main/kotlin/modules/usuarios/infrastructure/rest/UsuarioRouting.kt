package com.finanzasana.modules.usuarios.infrastructure.rest

import com.finanzasana.common.infrastructure.security.JwtConfig
import com.finanzasana.modules.usuarios.application.usecase.LoginUseCase
import com.finanzasana.modules.usuarios.application.usecase.RegistrarUsuarioUseCase
import com.finanzasana.modules.usuarios.application.usecase.VerPerfilUseCase
import com.finanzasana.modules.usuarios.application.usecase.VerUsuariosAdminUseCase
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
    val verUsuariosAdminUseCase by inject<VerUsuariosAdminUseCase>()

    // ============================
    // RUTAS PÚBLICAS
    // ============================
    route("/auth") {

        // LOGIN
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

    // ============================
    // REGISTRO PÚBLICO
    // ============================
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

    // ============================
    // RUTAS PROTEGIDAS
    // ============================
    authenticate("auth-jwt") {
        route("/usuarios") {

            // PERFIL DEL USUARIO LOGUEADO
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

            // LISTA DE USUARIOS (solo admin)
            get("/lista") {
                val principal = call.principal<JWTPrincipal>()!!
                val idSolicitante = principal.payload.getClaim("id").asInt()

                val lista = verUsuariosAdminUseCase.ejecutar(idSolicitante)

                if (lista == null) {
                    return@get call.respond(
                        HttpStatusCode.Forbidden,
                        mapOf("error" to "No tienes permisos para ver la lista de usuarios")
                    )
                }

                call.respond(HttpStatusCode.OK, lista.map { it.toResponse() })
            }
        }
    }
}
