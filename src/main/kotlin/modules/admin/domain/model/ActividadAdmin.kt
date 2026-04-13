package com.finanzasana.modules.admin.domain.model

import java.time.LocalDateTime

data class ActividadAdmin(
    val usuario: String,
    val accion: String,
    val fecha: LocalDateTime
) {

    init {
        require(usuario.isNotBlank()) { "El nombre del usuario no puede estar vacío" }
        require(accion.isNotBlank()) { "La acción no puede estar vacía" }
        // Para LocalDateTime validamos que no sea nulo y que sea una fecha válida
        require(fecha != null) { "La fecha no puede ser nula" }
    }
}
