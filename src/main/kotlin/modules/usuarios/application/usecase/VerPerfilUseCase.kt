package com.finanzasana.modules.usuarios.application.usecase

import com.finanzasana.modules.usuarios.domain.model.Usuario
import com.finanzasana.modules.usuarios.domain.repository.UsuarioRepository

class VerPerfilUseCase(
    private val usuarioRepository: UsuarioRepository
) {

    suspend fun ejecutar(idUsuario: Int): Usuario? {
        return usuarioRepository.verPorId(idUsuario)
    }
}
