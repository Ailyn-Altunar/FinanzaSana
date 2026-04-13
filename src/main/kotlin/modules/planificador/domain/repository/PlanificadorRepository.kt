package com.finanzasana.modules.planificador.domain.repository

import com.finanzasana.modules.planificador.domain.model.PlanificadorResultado

interface PlanificadorRepository {

    suspend fun generarPlan(
        idUsuario: Int,
        metodo: String
    ): PlanificadorResultado
}
