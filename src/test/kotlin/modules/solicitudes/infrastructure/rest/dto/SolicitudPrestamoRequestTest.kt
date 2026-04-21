package com.finanzasana.modules.solicitudes.infrastructure.rest.dto

import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class SolicitudPrestamoRequestTest {

    @Test
    fun `permite omitir campos opcionales al crear solicitud`() {
        val payload = """
            {
              "idUsuario": 1,
              "idEmpresa": 2,
              "montoSolicitado": 1000.0,
              "meses": 6,
              "motivo": "Pago de emergencia",
              "idCategoria": 3
            }
        """.trimIndent()

        val request = Json.decodeFromString<SolicitudPrestamoRequest>(payload)

        assertEquals(1, request.idUsuario)
        assertEquals(2, request.idEmpresa)
        assertEquals(1000.0, request.montoSolicitado)
        assertEquals(6, request.meses)
        assertEquals("Pago de emergencia", request.motivo)
        assertEquals(3, request.idCategoria)
        assertNull(request.imagenBase64)
        assertNull(request.latitud)
        assertNull(request.longitud)
    }
}
