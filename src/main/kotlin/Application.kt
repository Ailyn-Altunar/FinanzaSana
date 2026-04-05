package com.finanzasana

import com.finanzasana.common.infrastructure.serialization.DatabaseFactory
import com.finanzasana.modules.usuarios.usuarioModule
import com.finanzasana.modules.catalogoRol.rolModule
import com.finanzasana.modules.catalogoRol.infrastructure.rest.rolRouting
import com.finanzasana.modules.usuarios.infrastructure.rest.usuarioRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    DatabaseFactory.init()


    // Seguridad (si ya la tienes configurada)
    configureSecurity()

    // 1. Inyección de dependencias con Koin
    install(Koin) {
        slf4jLogger()
        modules(
            usuarioModule,
            rolModule
        )
    }

    // 2. Serialización JSON
    install(ContentNegotiation) {
        json()
    }

    // 3. Rutas
    routing {
        usuarioRouting()
        rolRouting()

    }
}