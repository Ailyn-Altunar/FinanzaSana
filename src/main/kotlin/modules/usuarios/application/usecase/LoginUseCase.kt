package com.finanzasana.modules.usuarios.application.usecase

import com.finanzasana.modules.usuarios.domain.model.Usuario
import com.finanzasana.modules.usuarios.domain.repository.UsuarioRepository
import org.mindrot.jbcrypt.BCrypt

class LoginUseCase(
    private val usuarioRepository: UsuarioRepository
) {

    suspend fun ejecutar(email: String, contrasena: String): Usuario {
        val usuario = usuarioRepository.verPorEmail(email)
            ?: throw IllegalArgumentException("El usuario no existe")

        val contrasenaValida = BCrypt.checkpw(contrasena, usuario.contrasena)
        if (!contrasenaValida) {
            throw IllegalArgumentException("Contraseña incorrecta")
        }

        return usuario
    }
}