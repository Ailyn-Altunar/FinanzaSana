package com.finanzasana.modules.estados

import com.finanzasana.modules.estados.domain.repository.EstadoSolicitudRepository
import com.finanzasana.modules.estados.infrastructure.persistence.PostgresEstadoSolicitudRepository
import org.koin.dsl.module

val estadoSolicitudModule = module {

    single<EstadoSolicitudRepository> { PostgresEstadoSolicitudRepository() }
}
