package com.finanzasana.modules.planificador.application.usecase

import com.finanzasana.modules.planificador.domain.model.PlanificadorResultado
import com.finanzasana.modules.planificador.domain.repository.PlanificadorRepository

class GenerarPlanificadorUseCase(
    private val planificadorRepository: PlanificadorRepository
) {

    suspend operator fun invoke(idUsuario: Int, metodo: String): PlanificadorResultado {
        return planificadorRepository.generarPlan(idUsuario, metodo)
    }
}
