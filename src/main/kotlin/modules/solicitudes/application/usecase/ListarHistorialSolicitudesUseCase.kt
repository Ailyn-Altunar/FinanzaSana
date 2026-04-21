package com.finanzasana.modules.solicitudes.application.usecase

import com.finanzasana.modules.solicitudes.domain.model.HistorialSolicitud
import com.finanzasana.modules.solicitudes.domain.repository.SolicitudPrestamoRepository

class ListarHistorialSolicitudesUseCase(
    private val solicitudRepository: SolicitudPrestamoRepository
) {

    suspend fun ejecutar(): List<HistorialSolicitud> {
        return solicitudRepository.listarHistorial()
    }
}
