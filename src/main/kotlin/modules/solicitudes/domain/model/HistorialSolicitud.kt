package com.finanzasana.modules.solicitudes.domain.model

data class HistorialSolicitud(
    val id: Int,
    val nombreUsuario: String,
    val nombreEmpresa: String,
    val montoSolicitado: Double,
    val estado: Int
) {

    init {
        require(id > 0) { "El id de la solicitud debe ser valido" }
        require(nombreUsuario.isNotBlank()) { "El nombre del usuario no puede estar vacio" }
        require(nombreEmpresa.isNotBlank()) { "El nombre de la empresa no puede estar vacio" }
        require(montoSolicitado > 0) { "El monto solicitado debe ser mayor a 0" }
        require(estado == 2 || estado == 3) { "El estado debe ser APROBADA o RECHAZADA" }
    }
}
