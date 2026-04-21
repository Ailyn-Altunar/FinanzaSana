package com.finanzasana.modules.usuarios.domain.model

data class Usuario(
    val id: Int? = null,
    val nombre: String,
    val email: String,
    val contrasena: String,
    val idRol: Int,
    val telefono: String,
    val activo: Boolean = true
) {

    init {
        require(nombre.isNotBlank()) { "El nombre no puede estar vacío" }
        require(email.isNotBlank()) { "El email no puede estar vacío" }
        require(contrasena.isNotBlank()) { "La contraseña no puede estar vacía" }
        require(idRol in 1..3) { "El rol debe ser válido (1=Admin, 2=Cliente)" }
        require(telefono.isNotBlank()) { "El número de teléfono no puede estar vacío" }
    }
}
