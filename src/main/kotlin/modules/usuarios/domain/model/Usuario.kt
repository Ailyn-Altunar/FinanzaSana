package com.finanzasana.modules.usuarios.domain.model

data class Usuario(
    val id: Int? = null,
    val nombre: String,
    val email: String,
    val contraseña: String,
    val idRol: Int,
    val nombreRol: String? = null
) {

    init {
        require(nombre.isNotBlank()) { "El nombre no puede estar vacío" }
        require(email.isNotBlank()) { "El email no puede estar vacío" }
        require(contraseña.isNotBlank()) { "La contraseña no puede estar vacía" }
        require(idRol in 1..3) { "El rol debe ser válido (1=Admin, 2=Cliente)" }
    }
}