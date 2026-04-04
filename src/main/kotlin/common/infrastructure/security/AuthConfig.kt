package com.finanzasana.common.infrastructure.security

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {

    install(Authentication) {

        jwt("auth-jwt") {   // Nombre del proveedor de autenticación

            verifier(JwtConfig.verifier)   // Usa el verificador definido en JwtConfig

            validate { credential ->

                // Extraemos los claims que guardamos en el token
                val id = credential.payload.getClaim("id").asInt()
                val rol = credential.payload.getClaim("idRol").asInt()

                // Si ambos existen, el token es válido
                if (id != null && rol != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null   // Token inválido → acceso denegado
                }
            }
        }
    }
}