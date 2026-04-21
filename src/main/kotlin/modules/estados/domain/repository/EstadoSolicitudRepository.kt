package com.finanzasana.modules.estados.domain.repository

import com.finanzasana.modules.estados.domain.model.EstadoSolicitud

interface EstadoSolicitudRepository {

    suspend fun obtenerPorId(id: Int): EstadoSolicitud?

    suspend fun listar(): List<EstadoSolicitud>
}
