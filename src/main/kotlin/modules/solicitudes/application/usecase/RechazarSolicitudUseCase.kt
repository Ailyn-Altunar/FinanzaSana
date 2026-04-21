package com.finanzasana.modules.solicitudes.application.usecase

import com.finanzasana.modules.solicitudes.domain.repository.SolicitudPrestamoRepository

class RechazarSolicitudUseCase(
    private val solicitudRepository: SolicitudPrestamoRepository
) {

    suspend fun ejecutar(idSolicitud: Int) {

        val solicitud = solicitudRepository.obtenerPorId(idSolicitud)
            ?: throw IllegalArgumentException("La solicitud no existe")

        // 2️⃣ Validar que esté pendiente
        if (solicitud.estado != 1) { // 1 = PENDIENTE
            throw IllegalStateException("La solicitud no está pendiente")
        }

        // 3️⃣ Actualizar estado a RECHAZADA
        solicitudRepository.actualizarEstado(idSolicitud, 3) // 3 = RECHAZADA
    }
}
