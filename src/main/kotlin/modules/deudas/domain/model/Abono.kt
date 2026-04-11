package com.finanzasana.modules.deudas.domain.model

import java.time.LocalDateTime

data class Abono(
    val id: Int? = null,
    val idDeuda: Int,
    val idUsuario: Int,
    val monto: Double,
    val fecha: LocalDateTime
) {
    init {
        require(idDeuda > 0) { "La deuda debe ser válida" }
        require(idUsuario > 0) { "El usuario debe ser válido" }
        require(monto > 0) { "El monto del abono debe ser mayor a 0" }
    }
}
