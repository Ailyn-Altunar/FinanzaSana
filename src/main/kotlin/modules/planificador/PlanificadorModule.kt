package com.finanzasana.modules.planificador

import com.finanzasana.modules.planificador.application.usecase.GenerarPlanificadorUseCase
import com.finanzasana.modules.planificador.domain.repository.PlanificadorRepository
import com.finanzasana.modules.planificador.infrastructure.persistence.PostgresPlanificadorRepository
import org.koin.dsl.module

val PlanificadorModule = module {

    factory { GenerarPlanificadorUseCase(get()) }

    single<PlanificadorRepository> {
        PostgresPlanificadorRepository()
    }
}
