package com.finanzasana.modules.empresas.domain.model

data class EmpresaPrestamo(
    val id: Int? = null,
    val nombre: String,
    val tasaInteres: Double
) {
    init {
        require(nombre.isNotBlank()) { "El nombre de la empresa no puede estar vacío" }
        require(tasaInteres >= 0) { "La tasa de interés no puede ser negativa" }
    }
}
