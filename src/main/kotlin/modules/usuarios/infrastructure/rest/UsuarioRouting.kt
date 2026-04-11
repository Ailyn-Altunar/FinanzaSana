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

    // ============================
    // Inyección de dependencias
    // ============================
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

            val usuario = try {
                loginUseCase.ejecutar(request.email, request.contrasena)
            } catch (e: Exception) {
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
    // RUTAS PROTEGIDAS
    // ============================
    authenticate("auth-jwt") {
        route("/usuarios") {

            // PERFIL DEL USUARIO LOGUEADO
            get("/perfil") {
                val principal = call.principal<JWTPrincipal>()!!
                val id = principal.payload.getClaim("id").asInt()

                val usuario = verPerfilUseCase.ejecutar(id)
                call.respond(HttpStatusCode.OK, usuario.toResponse())
            }

            // LISTA DE USUARIOS (solo admin)
            get("/lista") {
                val principal = call.principal<JWTPrincipal>()!!
                val idSolicitante = principal.payload.getClaim("id").asInt()

                try {
                    val lista = verUsuariosAdminUseCase.ejecutar(idSolicitante)
                    call.respond(HttpStatusCode.OK, lista.map { it.toResponse() })
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.Forbidden, mapOf("error" to e.message))
                }
            }

            // CREAR USUARIO (solo admin)
            post("/crear") {
                val principal = call.principal<JWTPrincipal>()!!
                val idSolicitante = principal.payload.getClaim("id").asInt()

                val solicitante = verPerfilUseCase.ejecutar(idSolicitante)
                if (solicitante.idRol != 1) {
                    return@post call.respond(
                        HttpStatusCode.Forbidden,
                        mapOf("error" to "No tienes permisos para crear usuarios")
                    )
                }

                val request = call.receive<UsuarioRequest>()

                val nuevo = registrarUsuarioUseCase.ejecutar(
                    nombre = request.nombre,
                    email = request.email,
                    contrasena = request.contrasena,
                    idRol = request.idRol,
                    telefono = request.telefono
                )

                call.respond(HttpStatusCode.Created, nuevo.toResponse())
            }
        }
    }
}
