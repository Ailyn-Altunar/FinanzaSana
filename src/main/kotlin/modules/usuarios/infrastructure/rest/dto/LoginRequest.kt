package com.finanzasana.modules.usuarios.infrastructure.rest.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val contraseña: String
)