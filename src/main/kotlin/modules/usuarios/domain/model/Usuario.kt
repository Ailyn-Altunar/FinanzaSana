package com.finanzasana.modules.usuarios.domain.model

data class Usuario(
    val id: Int? = null,
    val nombre: String,
    val email: String,
    val contrasena: String,
    val edad: Int,
    val idRol: Int,          // 1 = Admin, 2 = Cliente, 3 = SuperAdmin
    val nombreRol: String? = null
) {

    init {
        require(nombre.isNotBlank()) { "El nombre no puede estar vacío" }
        require(email.isNotBlank()) { "El email no puede estar vacío" }
        require(contrasena.isNotBlank()) { "La contraseña no puede estar vacía" }
        require(edad > 0) { "La edad debe ser mayor a 0" }
        require(idRol in 1..3) { "El rol debe ser válido (1=Admin, 2=Cliente, 3=SuperAdmin)" }
    }
}