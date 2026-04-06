package com.finanzasana.modules.usuarios.infrastructure.rest

import com.finanzasana.modules.usuarios.application.usecase.LoginUseCase
import com.finanzasana.modules.usuarios.application.usecase.RegistrarUsuarioUseCase
import com.finanzasana.modules.usuarios.application.usecase.VerPerfilUseCase
import com.finanzasana.modules.usuarios.application.usecase.VerUsuariosAdminUseCase
import com.finanzasana.modules.usuarios.infrastructure.rest.dto.LoginRequest
import com.finanzasana.modules.usuarios.infrastructure.rest.dto.UsuarioRequest
import com.finanzasana.modules.usuarios.infrastructure.rest.dto.toResponse
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

    // RUTA PÚBLICA: Solo para entrar al sistema
    route("/auth") {
        post("/login") {
            val request = call.receive<LoginRequest>()
            val usuario = try {
                loginUseCase.ejecutar(request.email, request.contraseña)
            } catch (e: Exception) {
                return@post call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Credenciales incorrectas"))
            }
            call.respond(HttpStatusCode.OK, usuario.toResponse())
        }
    }

    // RUTAS PROTEGIDAS: Requieren Token
    authenticate("auth-jwt") {
        route("/usuarios") {

            // CUALQUIERA LOGUEADO: Ver su propio perfil
            get("/perfil") {
                val principal = call.principal<JWTPrincipal>()!!
                val id = principal.payload.getClaim("id").asInt()
                val usuario = verPerfilUseCase.ejecutar(id)
                call.respond(HttpStatusCode.OK, usuario.toResponse())
            }

            // SOLO ADMIN: Ver lista de todos
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

            // SOLO ADMIN: Crear nuevo usuario
            post("/crear") {
                val principal = call.principal<JWTPrincipal>()!!
                val idSolicitante = principal.payload.getClaim("id").asInt()
                val request = call.receive<UsuarioRequest>()

                // Verificamos rol antes de procesar
                val solicitante = verPerfilUseCase.ejecutar(idSolicitante)
                if (solicitante.idRol != 1) {
                    return@post call.respond(HttpStatusCode.Forbidden, mapOf("error" to "No eres admin"))
                }

                val nuevo = registrarUsuarioUseCase.ejecutar(
                    request.nombre, request.email, request.contraseña, request.idRol
                )
                call.respond(HttpStatusCode.Created, nuevo.toResponse())
            }
        }
    }
}