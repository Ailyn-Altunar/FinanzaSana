package com.finanzasana.modules.solicitudes.application.usecase

import com.finanzasana.modules.deudas.domain.model.Deuda
import com.finanzasana.modules.deudas.domain.repository.DeudaRepository
import com.finanzasana.modules.solicitudes.domain.repository.SolicitudPrestamoRepository
import java.time.LocalDate

class AprobarSolicitudUseCase(
    private val solicitudRepository: SolicitudPrestamoRepository,
    private val deudaRepository: DeudaRepository
) {

    suspend fun ejecutar(idSolicitud: Int) {

        val solicitud = solicitudRepository.obtenerPorId(idSolicitud)
            ?: throw IllegalArgumentException("La solicitud no existe")

        if (solicitud.estado != 1) { // 1 = PENDIENTE
            throw IllegalStateException("La solicitud no está pendiente")
        }

        val fechaAprobacion = LocalDate.now()
        val fechaVencimiento = fechaAprobacion.plusMonths(solicitud.meses.toLong())

        val interes = solicitud.montoSolicitado *
                (solicitud.tasaInteres / 100.0) *
                (solicitud.meses / 12.0)

        val montoOriginal = solicitud.montoSolicitado + interes

        val deuda = Deuda(
            id = null,
            concepto = solicitud.motivo,
            montoOriginal = montoOriginal,
            saldoActual = montoOriginal,
            fechaVencimiento = fechaVencimiento,
            tasaInteres = solicitud.tasaInteres,
            idCategoria = solicitud.idCategoria,
            idUsuario = solicitud.idUsuario,
            imagenBase64 = solicitud.imagenBase64,
            latitud = solicitud.latitud,
            longitud = solicitud.longitud
        )

        deudaRepository.registrar(deuda)

        solicitudRepository.actualizarEstado(idSolicitud, 2) // 2 = APROBADA
    }
}
