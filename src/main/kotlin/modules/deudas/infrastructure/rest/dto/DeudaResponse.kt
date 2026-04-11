package com.finanzasana.modules.deudas.infrastructure.rest.dto

import com.finanzasana.modules.deudas.domain.model.Deuda
import kotlinx.serialization.Serializable
import java.time.format.DateTimeFormatter

@Serializable
data class DeudaResponse(
    val id: Int,
    val concepto: String,
    val montoOriginal: Double,
    val saldoActual: Double,
    val porcentajePagado: Double,
    val tasaInteres: Double? = null,
    val fechaVencimiento: String,
    val categoria: String,
    val abonos: List<AbonoResponse> = emptyList()
)

fun Deuda.toResponse(
    categoriaNombre: String,
    abonos: List<AbonoResponse>
): DeudaResponse {

    val formatter = DateTimeFormatter.ISO_DATE

    val porcentaje = if (montoOriginal > 0) {
        ((montoOriginal - saldoActual) / montoOriginal) * 100
    } else 0.0

    return DeudaResponse(
        id = this.id ?: 0,
        concepto = this.concepto,
        montoOriginal = this.montoOriginal,
        saldoActual = this.saldoActual,
        porcentajePagado = porcentaje,
        tasaInteres = this.tasaInteres,
        fechaVencimiento = this.fechaVencimiento.format(formatter),
        categoria = categoriaNombre,
        abonos = abonos
    )
}
