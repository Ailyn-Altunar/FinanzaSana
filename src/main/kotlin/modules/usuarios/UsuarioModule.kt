package com.finanzasana.modules.usuarios

import com.finanzasana.modules.usuarios.application.usecase.*
import com.finanzasana.modules.usuarios.domain.repository.UsuarioRepository
import com.finanzasana.modules.usuarios.infrastructure.persistence.PostgresUsuarioRepository
import org.koin.dsl.module

val usuarioModule = module {

    factory { LoginUseCase(get()) }
    factory { RegistrarUsuarioUseCase(get()) }
    factory { VerPerfilUseCase(get()) }
    factory { VerUsuariosAdminUseCase(get()) }
    factory { EliminarUsuarioUseCase(get()) }


    single<UsuarioRepository> { PostgresUsuarioRepository() }
}