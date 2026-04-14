package com.finanzasana.modules.usuarios.application.usecase

import com.finanzasana.modules.usuarios.domain.model.Usuario
import com.finanzasana.modules.usuarios.domain.repository.UsuarioRepository

class VerUsuariosAdminUseCase(
    private val usuarioRepository: UsuarioRepository
) {

    suspend fun ejecutar(idUsuarioSolicitante: Int): List<Usuario>? {

        val solicitante = usuarioRepository.verPorId(idUsuarioSolicitante)
            ?: return null

        if (solicitante.idRol != 1) return null

        return usuarioRepository.listar()
    }
}
