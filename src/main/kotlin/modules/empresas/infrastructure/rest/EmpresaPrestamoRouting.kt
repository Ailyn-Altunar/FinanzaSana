package com.finanzasana.modules.empresas.infrastructure.rest

import com.finanzasana.modules.empresas.application.usecase.ListarEmpresasUseCase
import com.finanzasana.modules.empresas.application.usecase.RegistrarEmpresaUseCase
import com.finanzasana.modules.empresas.infrastructure.rest.dto.EmpresaPrestamoRequest
import com.finanzasana.modules.empresas.infrastructure.rest.dto.EmpresaPrestamoResponse
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.empresaPrestamoRouting() {

    val registrarEmpresaUseCase by inject<RegistrarEmpresaUseCase>()
    val listarEmpresasUseCase by inject<ListarEmpresasUseCase>()

    route("/empresas") {

        post {
            val request = call.receive<EmpresaPrestamoRequest>()

            val empresa = registrarEmpresaUseCase.ejecutar(
                nombre = request.nombre,
                tasaInteres = request.tasaInteres
            )

            call.respond(
                EmpresaPrestamoResponse(
                    id = empresa.id ?: 0,
                    nombre = empresa.nombre,
                    tasaInteres = empresa.tasaInteres
                )
            )
        }

        get {
            val empresas = listarEmpresasUseCase.ejecutar()

            val response = empresas.map {
                EmpresaPrestamoResponse(
                    id = it.id ?: 0,
                    nombre = it.nombre,
                    tasaInteres = it.tasaInteres
                )
            }

            call.respond(response)
        }
    }
}
