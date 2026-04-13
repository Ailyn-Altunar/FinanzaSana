package com.finanzasana.modules.admin.domain.model



data class UserAdmin(
    val id: Int,
    val nombre: String,
    val email: String,
    val totalDeudas: Int
) {

    init {
        require(nombre.isNotBlank()) { "El nombre no puede estar vacío" }
        require(email.isNotBlank()) { "El email no puede estar vacío" }
        require(totalDeudas >= 0) { "El total de deudas no puede ser negativo" }
    }
}
