package com.finanzasana.common.infrastructure.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.finanzasana.modules.usuarios.domain.model.Usuario
import java.util.*

object JwtConfig {

    // 1. Llave secreta (se recomienda ponerla en variables de entorno)
    private val secret = System.getenv("JWT_SECRET") ?: "finanzasana_super_secreto_123456789"

    // 2. Identificador del emisor del token
    private const val issuer = "com.finanzasana.api"

    // 3. Algoritmo de firma
    private val algorithm = Algorithm.HMAC256(secret)

    // 4. Verificador que usará Ktor para validar tokens entrantes
    val verifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    // 5. Función para generar un token JWT cuando el usuario inicia sesión
    fun generateToken(usuario: Usuario): String =
        JWT.create()
            .withSubject("Authentication")
            .withIssuer(issuer)
            // Claims personalizados que tú necesitas
            .withClaim("id", usuario.id)
            .withClaim("idRol", usuario.idRol)
            // Expiración: 24 horas
            .withExpiresAt(Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24))
            .sign(algorithm)
}