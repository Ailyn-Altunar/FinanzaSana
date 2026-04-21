package com.finanzasana.modules.admin.domain.model

import java.time.LocalDateTime

data class ActividadAdmin(
    val nombreUsuario: String,
    val montoAbono: Double,
    val idDeuda: Int,
    val fecha: LocalDateTime
) {

    init {
        require(nombreUsuario.isNotBlank()) { "El nombre del usuario no puede estar vacio" }
        require(montoAbono > 0) { "El monto del abono debe ser mayor a 0" }
        require(idDeuda > 0) { "El id de la deuda debe ser valido" }
    }
}
