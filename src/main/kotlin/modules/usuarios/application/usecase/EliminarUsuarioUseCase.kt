package com.finanzasana.modules.usuarios.application.usecase

import com.finanzasana.modules.usuarios.domain.repository.UsuarioRepository

class EliminarUsuarioUseCase(
    private val usuarioRepository: UsuarioRepository
) {
    suspend operator fun invoke(id: Int): Boolean {
        return usuarioRepository.eliminar(id)
    }
}
