package com.finanzasana.modules.deudas.infrastructure.rest.dto

import com.finanzasana.modules.deudas.domain.model.Deuda
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class DeudaRequest(
    val concepto: String,
    val montoOriginal: Double,
    val tasaInteres: Double? = null,
    val idCategoria: Int,
    val fechaVencimiento: String
) {
    fun toDomain(idUsuario: Int) = Deuda(
        concepto = concepto,
        montoOriginal = montoOriginal,
        tasaInteres = tasaInteres,
        idCategoria = idCategoria,
        idUsuario = idUsuario,
        fechaVencimiento = LocalDate.parse(fechaVencimiento)
    )
}
