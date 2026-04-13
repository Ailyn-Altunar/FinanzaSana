package com.finanzasana.modules.planificador.infrastructure.rest.dto

import com.finanzasana.modules.deudas.infrastructure.rest.dto.DeudaResponse
import com.finanzasana.modules.planificador.domain.model.PlanificadorResultado
import kotlinx.serialization.Serializable

@Serializable
data class PlanificadorResponse(
    val metodo: String,
    val totalDeuda: Double,
    val tasaPromedio: Double,
    val deudasOrdenadas: List<DeudaResponse>
)

fun PlanificadorResultado.toResponse(
    deudaMapper: (List<com.finanzasana.modules.deudas.domain.model.Deuda>) -> List<DeudaResponse>
): PlanificadorResponse {
    return PlanificadorResponse(
        metodo = metodo,
        totalDeuda = totalDeuda,
        tasaPromedio = tasaPromedio,
        deudasOrdenadas = deudaMapper(deudasOrdenadas)
    )
}
