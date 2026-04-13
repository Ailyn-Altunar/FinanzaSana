package com.finanzasana.modules.admin.domain.model

data class AdminMetrics(
    val usuariosTotales: Int,
    val montoGlobal: Double,
    val deudasVencidas: Int
) {

    init {
        require(usuariosTotales >= 0) { "El número de usuarios no puede ser negativo" }
        require(montoGlobal >= 0) { "El monto global no puede ser negativo" }
        require(deudasVencidas >= 0) { "Las deudas vencidas no pueden ser negativas" }
    }
}
