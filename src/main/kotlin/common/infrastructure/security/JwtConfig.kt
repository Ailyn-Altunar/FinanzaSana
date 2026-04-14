package com.finanzasana.common.infrastructure.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.finanzasana.modules.usuarios.domain.model.Usuario
import java.util.*

object JwtConfig {

    private val secret = System.getenv("JWT_SECRET") ?: "finanzasana_super_secreto_123456789"

    private const val issuer = "com.finanzasana.api"

    private val algorithm = Algorithm.HMAC256(secret)

    val verifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(usuario: Usuario): String =
        JWT.create()
            .withSubject("Authentication")
            .withIssuer(issuer)
            .withClaim("id", usuario.id)
            .withClaim("idRol", usuario.idRol)
            .withExpiresAt(Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24))
            .sign(algorithm)
}