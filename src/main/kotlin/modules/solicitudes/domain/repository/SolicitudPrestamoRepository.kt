package com.finanzasana.modules.solicitudes.domain.repository

import com.finanzasana.modules.solicitudes.domain.model.PendienteSolicitud
import com.finanzasana.modules.solicitudes.domain.model.HistorialSolicitud
import com.finanzasana.modules.solicitudes.domain.model.SolicitudPrestamo

interface SolicitudPrestamoRepository {

    suspend fun registrar(solicitud: SolicitudPrestamo): SolicitudPrestamo

    suspend fun obtenerPorId(id: Int): SolicitudPrestamo?

    suspend fun listarPendientes(): List<SolicitudPrestamo>

    suspend fun listarPendientesAdmin(): List<PendienteSolicitud>

    suspend fun listarHistorial(): List<HistorialSolicitud>

    suspend fun actualizarEstado(id: Int, nuevoEstado: Int)
}
