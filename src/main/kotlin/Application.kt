package com.finanzasana

import com.finanzasana.common.infrastructure.security.configureSecurity
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

fun Application.module() {

    DatabaseFactory.init()

    // 2. Inyección de dependencias (Koin)
    // Debe ir antes de Seguridad y Routing por si necesitas inyectar algo ahí
    install(Koin) {
        slf4jLogger()
        modules(
            usuarioModule,
            rolModule
        )
    }

    // 3. Serialización JSON (Necesaria para que Auth pueda leer/escribir respuestas)
    install(ContentNegotiation) {
        json()
    }



    configureSecurity()


    routing {
        usuarioRouting()
        rolRouting()
    }
}