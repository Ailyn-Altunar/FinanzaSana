package com.finanzasana.modules.usuarios.application.usecase

import com.finanzasana.modules.usuarios.domain.model.Usuario
import com.finanzasana.modules.usuarios.domain.repository.UsuarioRepository
import org.mindrot.jbcrypt.BCrypt

class LoginUseCase(
    private val usuarioRepository: UsuarioRepository
) {

    suspend fun ejecutar(email: String, contrasena: String): Usuario? {

        if (email.isBlank()) return null
        if (contrasena.isBlank()) return null

        val usuario = usuarioRepository.verPorEmail(email)
            ?: return null

        if (!usuario.activo) return null

        val contrasenaValida = BCrypt.checkpw(contrasena, usuario.contrasena)
        if (!contrasenaValida) return null

        return usuario
    }
}
