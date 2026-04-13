package com.finanzasana.modules.admin.domain.repository

import com.finanzasana.modules.admin.domain.model.AdminMetrics
import com.finanzasana.modules.admin.domain.model.ActividadAdmin
import com.finanzasana.modules.admin.domain.model.UserAdmin

interface AdminRepository {

    suspend fun obtenerMetricas(): AdminMetrics

    suspend fun obtenerActividadReciente(): List<ActividadAdmin>

    suspend fun obtenerUsuariosAdmin(): List<UserAdmin>
}
