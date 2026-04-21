package com.finanzasana.modules.estados.domain.model

data class EstadoSolicitud(
    val id: Int? = null,
    val nombre: String
) {
    init {
        require(nombre.isNotBlank()) { "El nombre del estado no puede estar vacío" }
    }
}
