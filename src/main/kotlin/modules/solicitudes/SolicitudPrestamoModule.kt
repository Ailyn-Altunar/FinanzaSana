package com.finanzasana.modules.solicitudes

import com.finanzasana.modules.solicitudes.application.usecase.*
import com.finanzasana.modules.solicitudes.domain.repository.SolicitudPrestamoRepository
import com.finanzasana.modules.solicitudes.infrastructure.persistence.PostgresSolicitudPrestamoRepository
import org.koin.dsl.module

val solicitudPrestamoModule = module {

    factory { CrearSolicitudUseCase(get(), get()) }
    factory { ListarHistorialSolicitudesUseCase(get()) }
    factory { ListarSolicitudesPendientesAdminUseCase(get()) }
    factory { AprobarSolicitudUseCase(get(), get()) }
    factory { RechazarSolicitudUseCase(get()) }

    single<SolicitudPrestamoRepository> { PostgresSolicitudPrestamoRepository() }
}
