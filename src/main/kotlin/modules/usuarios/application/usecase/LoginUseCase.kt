package com.finanzasana.modules.usuarios.application.usecase

import com.finanzasana.modules.usuarios.domain.model.Usuario
import com.finanzasana.modules.usuarios.domain.repository.UsuarioRepository
import org.mindrot.jbcrypt.BCrypt

class LoginUseCase(
    private val usuarioRepository: UsuarioRepository
) {

    suspend fun ejecutar(email: String, contrasena: String): Usuario {
        // 1. Buscar usuario por email
        val usuario = usuarioRepository.verPorEmail(email)
            ?: throw IllegalArgumentException("El usuario no existe")

        // 2. Validar contraseña
        val contrasenaValida = BCrypt.checkpw(contrasena, usuario.contrasena)
        if (!contrasenaValida) {
            throw IllegalArgumentException("Contraseña incorrecta")
        }

        // 3. Si todo está bien, devolver el usuario
        return usuario
    }
}