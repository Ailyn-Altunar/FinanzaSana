package com.finanzasana.modules.planificador.domain.model

import com.finanzasana.modules.deudas.domain.model.Deuda

data class PlanificadorResultado(
    val metodo: String,
    val totalDeuda: Double,
    val tasaPromedio: Double,
    val deudasOrdenadas: List<Deuda>
)
