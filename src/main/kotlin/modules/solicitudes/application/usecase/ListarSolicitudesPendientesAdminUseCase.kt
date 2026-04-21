package com.finanzasana.modules.solicitudes.application.usecase

import com.finanzasana.modules.solicitudes.domain.model.PendienteSolicitud
import com.finanzasana.modules.solicitudes.domain.repository.SolicitudPrestamoRepository

class ListarSolicitudesPendientesAdminUseCase(
    private val solicitudRepository: SolicitudPrestamoRepository
) {

    suspend fun ejecutar(): List<PendienteSolicitud> {
        return solicitudRepository.listarPendientesAdmin()
    }
}
