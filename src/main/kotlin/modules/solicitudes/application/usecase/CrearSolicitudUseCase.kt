package com.finanzasana.modules.solicitudes.application.usecase

import com.finanzasana.modules.solicitudes.domain.model.SolicitudPrestamo
import com.finanzasana.modules.solicitudes.domain.repository.SolicitudPrestamoRepository
import com.finanzasana.modules.empresas.domain.repository.EmpresaPrestamoRepository
import java.time.LocalDateTime

class CrearSolicitudUseCase(
    private val solicitudRepository: SolicitudPrestamoRepository,
    private val empresaRepository: EmpresaPrestamoRepository
) {

    suspend fun ejecutar(
        idUsuario: Int,
        idEmpresa: Int,
        montoSolicitado: Double,
        meses: Int,
        motivo: String,
        idCategoria: Int,
        imagenBase64: String?,
        latitud: Double?,
        longitud: Double?
    ): SolicitudPrestamo {

        val empresa = empresaRepository.obtenerPorId(idEmpresa)
            ?: throw IllegalArgumentException("La empresa no existe")

        val solicitud = SolicitudPrestamo(
            id = null,
            idUsuario = idUsuario,
            idEmpresa = idEmpresa,
            montoSolicitado = montoSolicitado,
            meses = meses,
            motivo = motivo,
            tasaInteres = empresa.tasaInteres,
            idCategoria = idCategoria,
            imagenBase64 = imagenBase64,
            latitud = latitud,
            longitud = longitud,
            estado = 1,
            fechaSolicitud = LocalDateTime.now()
        )

        return solicitudRepository.registrar(solicitud)
    }
}
