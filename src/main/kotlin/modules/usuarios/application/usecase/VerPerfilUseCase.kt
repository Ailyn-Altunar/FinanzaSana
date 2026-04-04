
package com.finanzasana.modules.usuarios.application.usecase

import com.finanzasana.modules.usuarios.domain.model.Usuario
import com.finanzasana.modules.usuarios.domain.repository.UsuarioRepository

class VerPerfilUseCase(
    private val usuarioRepository: UsuarioRepository
) {

    suspend fun ejecutar(idUsuario: Int): Usuario {
        val usuario = usuarioRepository.verPorId(idUsuario)
            ?: throw IllegalArgumentException("El usuario no existe")

        return usuario
    }
}