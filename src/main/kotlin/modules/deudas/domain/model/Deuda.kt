package com.finanzasana.modules.deudas.domain.model

import java.time.LocalDate

data class Deuda(
    val id: Int? = null,
    val concepto: String,
    val montoOriginal: Double,
    val saldoActual: Double = montoOriginal,
    val fechaVencimiento: LocalDate,
    val tasaInteres: Double,
    val idCategoria: Int,
    val idUsuario: Int,
    val imagenBase64: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null
) {
    init {
        require(concepto.isNotBlank()) { "El concepto no puede estar vacío" }
        require(montoOriginal > 0) { "El monto original debe ser mayor a 0" }
        require(saldoActual >= 0) { "El saldo actual no puede ser negativo" }
        require(tasaInteres >= 0) { "La tasa de interés no puede ser negativa" }
        require(idCategoria > 0) { "La categoría debe ser válida" }   // ✔ obligatorio
        require(idUsuario > 0) { "El usuario debe ser válido" }
    }
}
