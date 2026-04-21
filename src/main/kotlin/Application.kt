package com.finanzasana

import com.finanzasana.common.infrastructure.security.configureSecurity
import com.finanzasana.common.infrastructure.serialization.DatabaseFactory
import com.finanzasana.modules.admin.adminModule
import com.finanzasana.modules.admin.infrastructure.rest.adminRouting
import com.finanzasana.modules.usuarios.usuarioModule
import com.finanzasana.modules.catalogoRol.rolModule
import com.finanzasana.modules.deudas.DeudaModule
import com.finanzasana.modules.deudas.infrastructure.rest.abonoRouting
import com.finanzasana.modules.deudas.infrastructure.rest.deudaRouting
import com.finanzasana.modules.usuarios.infrastructure.rest.usuarioRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import com.finanzasana.modules.categorias.categoriaModule
import com.finanzasana.modules.categorias.infrastructure.rest.categoriaRouting
import com.finanzasana.modules.empresas.empresaPrestamoModule
import com.finanzasana.modules.empresas.infrastructure.rest.empresaPrestamoRouting
import com.finanzasana.modules.estados.estadoSolicitudModule
import com.finanzasana.modules.planificador.PlanificadorModule
import com.finanzasana.modules.planificador.infrastructure.rest.planificadorRouting
import com.finanzasana.modules.solicitudes.infrastructure.rest.solicitudPrestamoRouting
import com.finanzasana.modules.solicitudes.solicitudPrestamoModule
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.cors.routing.CORS


fun Application.module() {

    DatabaseFactory.init()

    install(Koin) {
        slf4jLogger()
        modules(
            usuarioModule,
            rolModule,
            DeudaModule,
            categoriaModule,
            adminModule,
            PlanificadorModule,
            solicitudPrestamoModule,
            estadoSolicitudModule,
            empresaPrestamoModule

        )
    }

    install(ContentNegotiation) {
        json()
    }



    configureSecurity()
    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
        allowNonSimpleContentTypes = true
        allowCredentials = true
    }


    routing {
        usuarioRouting()
        deudaRouting()
        abonoRouting()
        categoriaRouting()
        adminRouting()
        planificadorRouting()
        empresaPrestamoRouting()
        solicitudPrestamoRouting()
    }
}
fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0") { // <--- 0.0.0.0 permite conexiones externas
        module()
    }.start(wait = true)
}