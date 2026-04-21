package com.finanzasana.modules.deudas.infrastructure.rest.dto

import com.finanzasana.modules.deudas.domain.model.Deuda
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Serializable
data class DeudaResponse(
    val id: Int,
    val concepto: String,
    val montoOriginal: Double,
    val saldoActual: Double,
    val porcentajePagado: Double,
    val tasaInteres: Double,
    val fechaVencimiento: String,
    val estadoDeuda: String,
    val categoria: String,
    val imagenBase64: String? = null,
    val latitud: Double? = null,
    val longitud: Double? = null,
    val abonos: List<AbonoResponse> = emptyList()
)

fun Deuda.toResponse(
    categoriaNombre: String,
    abonos: List<AbonoResponse>
): DeudaResponse {

    val formatter = DateTimeFormatter.ISO_DATE
    val hoy = LocalDate.now()

    val porcentaje = if (montoOriginal > 0) {
        ((montoOriginal - saldoActual) / montoOriginal) * 100
    } else 0.0

    val estadoDeuda = when {
        this.saldoActual <= 0 -> "LIQUIDADA"
        this.fechaVencimiento.isBefore(hoy) -> "VENCIDA"
        else -> "ACTIVA"
    }

    return DeudaResponse(
        id = this.id ?: 0,
        concepto = this.concepto,
        montoOriginal = this.montoOriginal,
        saldoActual = this.saldoActual,
        porcentajePagado = porcentaje,
        tasaInteres = this.tasaInteres ?: 0.0,
        fechaVencimiento = this.fechaVencimiento.format(formatter),
        estadoDeuda = estadoDeuda,
        categoria = categoriaNombre,
        imagenBase64 = this.imagenBase64,
        latitud = this.latitud,
        longitud = this.longitud,
        abonos = abonos
    )
}
